import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { finalize } from 'rxjs';

import { AuthService } from '../../../core/services/auth.service';

@Component({
  standalone: true,
  selector: 'app-login',
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  readonly loginForm = this.fb.nonNullable.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required]]
  });

  submitting = false;

  constructor(
    private readonly fb: FormBuilder,
    private readonly authService: AuthService,
    private readonly router: Router
  ) {}

  onSubmit(): void {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    this.submitting = true;
    this.authService
      .login(this.loginForm.getRawValue())
      .pipe(finalize(() => (this.submitting = false)))
      .subscribe({
        next: (result) => {
          if (this.isAdminRole(result.role)) {
            this.router.navigate(['/admin']);
            return;
          }

          this.router.navigate(['/']);
        },
        error: (error: Error) => {
          console.error('Login failed', error);
          alert(error.message || 'Login failed. Please check your credentials.');
        }
      });
  }

  private isAdminRole(role: string): boolean {
    return role.trim().toUpperCase() === 'ADMIN';
  }

  isInvalid(controlName: 'email' | 'password', errorCode?: string): boolean {
    const control = this.loginForm.controls[controlName];

    if (errorCode) {
      return control.touched && control.hasError(errorCode);
    }

    return control.touched && control.invalid;
  }
}
