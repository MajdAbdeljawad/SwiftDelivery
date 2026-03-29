import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError, tap } from 'rxjs';

import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private readonly baseUrl = 'http://localhost:8085/api/users';

  constructor(private readonly http: HttpClient) {}

  getAllUsers(): Observable<User[]> {
    const url = `${this.baseUrl}/getAll`;
    console.log('[UserService] GET', url);
    return this.http.get<User[]>(url).pipe(
      tap((response) => console.log('[UserService] GET getAll response', response)),
      catchError((error) => this.handleError('getAllUsers', error))
    );
  }

  getUserById(id: number): Observable<User> {
    const url = `${this.baseUrl}/getById/${id}`;
    console.log('[UserService] GET', url);
    return this.http.get<User>(url).pipe(
      tap((response) => console.log('[UserService] GET getById response', response)),
      catchError((error) => this.handleError('getUserById', error))
    );
  }

  addUser(user: Omit<User, 'id'>): Observable<User> {
    const url = `${this.baseUrl}/add`;
    console.log('[UserService] POST', url, user);
    return this.http.post<User>(url, user).pipe(
      tap((response) => console.log('[UserService] POST add response', response)),
      catchError((error) => this.handleError('addUser', error))
    );
  }

  updateUser(id: number, user: Partial<User>): Observable<User> {
    const url = `${this.baseUrl}/editUser/${id}`;
    console.log('[UserService] PUT', url, user);
    return this.http.put<User>(url, user).pipe(
      tap((response) => console.log('[UserService] PUT editUser response', response)),
      catchError((error) => this.handleError('updateUser', error))
    );
  }

  deleteUser(id: number): Observable<unknown> {
    const url = `${this.baseUrl}/deleteUser/${id}`;
    console.log('[UserService] DELETE', url);
    return this.http.delete<unknown>(url).pipe(
      tap((response) => console.log('[UserService] DELETE deleteUser response', response)),
      catchError((error) => this.handleError('deleteUser', error))
    );
  }

  private handleError(operation: string, error: HttpErrorResponse): Observable<never> {
    console.error(`[UserService] ${operation} failed`, error);
    return throwError(() => new Error(error.error?.message || error.message || `Failed to ${operation}`));
  }
}