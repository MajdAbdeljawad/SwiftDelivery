import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, map, throwError } from 'rxjs';

export interface RegisterPayload {
  fullName: string;
  email: string;
  password: string;
  phone: string;
  address: string;
}

export interface LoginPayload {
  email: string;
  password: string;
}

export interface LoginResult {
  role: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly baseUrl = 'http://localhost:8085/api/auth';
  private readonly roleStorageKey = 'auth_role';
  private readonly nameStorageKey = 'auth_name';
  private readonly loginFlagStorageKey = 'auth_logged_in';

  constructor(private readonly http: HttpClient) {}

  register(data: RegisterPayload): Observable<unknown> {
    return this.http.post<unknown>(`${this.baseUrl}/register`, data).pipe(
      catchError((error) => this.handleError('register', error))
    );
  }

  login(data: LoginPayload): Observable<LoginResult> {
    return this.http.post<unknown>(`${this.baseUrl}/login`, data).pipe(
      map((response) => {
        const role = this.extractRole(response) ?? 'USER';
        const fullName = this.extractFullName(response) ?? this.nameFromEmail(data.email);

        localStorage.setItem(this.roleStorageKey, role);
        localStorage.setItem(this.nameStorageKey, fullName);
        localStorage.setItem(this.loginFlagStorageKey, 'true');

        return { role };
      }),
      catchError((error) => this.handleError('login', error))
    );
  }

  getToken(): string | null {
    return null;
  }

  getRole(): string | null {
    const storedRole = localStorage.getItem(this.roleStorageKey);
    if (storedRole) {
      return storedRole;
    }

    return null;
  }

  getDisplayName(): string {
    return localStorage.getItem(this.nameStorageKey) || 'User';
  }

  isLoggedIn(): boolean {
    return localStorage.getItem(this.loginFlagStorageKey) === 'true';
  }

  logout(): void {
    localStorage.removeItem(this.roleStorageKey);
    localStorage.removeItem(this.nameStorageKey);
    localStorage.removeItem(this.loginFlagStorageKey);
  }

  private extractRole(body: unknown): string | null {
    if (body && typeof body === 'object') {
      const candidate = body as Record<string, unknown>;
      const roleCandidate = candidate['role'] ?? candidate['userRole'] ?? candidate['authority'];

      if (typeof roleCandidate === 'string' && roleCandidate.trim()) {
        return this.normalizeRole(roleCandidate);
      }

      const rolesCandidate = candidate['roles'] ?? candidate['authorities'];
      if (Array.isArray(rolesCandidate) && rolesCandidate.length > 0) {
        const firstRole = rolesCandidate.find((item) => typeof item === 'string');
        if (typeof firstRole === 'string') {
          return this.normalizeRole(firstRole);
        }
      }

      const nestedRole = this.findStringInObject(candidate, [
        ['data', 'role'],
        ['data', 'userRole'],
        ['data', 'authority'],
        ['result', 'role'],
        ['result', 'userRole'],
        ['authenticationResponse', 'role']
      ]);

      if (nestedRole) {
        return this.normalizeRole(nestedRole);
      }
    }

    return null;
  }

  private normalizeRole(rawRole: string): string {
    const normalized = rawRole.trim().toUpperCase().replace(/^ROLE_/, '');
    return normalized;
  }

  private extractFullName(body: unknown): string | null {
    if (!body || typeof body !== 'object') {
      return null;
    }

    const candidate = body as Record<string, unknown>;
    const directName = candidate['fullName'] ?? candidate['name'] ?? candidate['username'];

    if (typeof directName === 'string' && directName.trim()) {
      return directName.trim();
    }

    return this.findStringInObject(candidate, [
      ['data', 'fullName'],
      ['data', 'name'],
      ['user', 'fullName'],
      ['user', 'name'],
      ['result', 'fullName'],
      ['result', 'name'],
      ['authenticationResponse', 'fullName']
    ]);
  }

  private nameFromEmail(email: string): string {
    const base = email.split('@')[0] || 'User';
    return base
      .split(/[._-]+/)
      .filter(Boolean)
      .map((part) => part.charAt(0).toUpperCase() + part.slice(1))
      .join(' ');
  }

  private findStringInObject(source: Record<string, unknown>, paths: string[][]): string | null {
    for (const path of paths) {
      let current: unknown = source;

      for (const segment of path) {
        if (!current || typeof current !== 'object') {
          current = null;
          break;
        }

        current = (current as Record<string, unknown>)[segment];
      }

      if (typeof current === 'string' && current.trim()) {
        return current.trim();
      }
    }

    return null;
  }

  private handleError(operation: string, error: HttpErrorResponse | Error): Observable<never> {
    if (error instanceof HttpErrorResponse) {
      const apiMessage = this.resolveApiErrorMessage(operation, error) || `Failed to ${operation}`;

      return throwError(() => new Error(apiMessage));
    }

    return throwError(() => error);
  }

  private resolveApiErrorMessage(operation: string, error: HttpErrorResponse): string {
    if (typeof error.error === 'string' && error.error.trim()) {
      return error.error;
    }

    if (error.error && typeof error.error === 'object') {
      const payload = error.error as Record<string, unknown>;
      const directMessage = payload['message'] ?? payload['error'] ?? payload['details'] ?? payload['description'];

      if (typeof directMessage === 'string' && directMessage.trim()) {
        return directMessage;
      }
    }

    if (error.status === 500 && operation === 'login') {
      return 'Invalid email or password.';
    }

    if (error.status === 500 && operation === 'register') {
      return 'Registration failed. Please verify your data and try again.';
    }

    return error.message;
  }
}