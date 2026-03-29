import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { finalize } from 'rxjs';

import { User } from '../../../core/models/user.model';
import { UserService } from '../../../core/services/user.service';

type UserForm = {
  fullName: FormControl<string>;
  email: FormControl<string>;
  password: FormControl<string>;
  phone: FormControl<string>;
  address: FormControl<string>;
  role: FormControl<string>;
};

@Component({
  selector: 'app-admin-users',
  templateUrl: './admin-users.component.html',
  styleUrls: ['./admin-users.component.css']
})
export class AdminUsersComponent implements OnInit {
  users: User[] = [];

  loading = false;
  submitting = false;
  formVisible = false;
  isEditMode = false;
  selectedUserId: number | null = null;

  successMessage = '';
  errorMessage = '';

  readonly userForm: FormGroup<UserForm>;

  constructor(
    private readonly fb: FormBuilder,
    private readonly userService: UserService
  ) {
    this.userForm = this.fb.nonNullable.group({
      fullName: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: [''],
      phone: [''],
      address: [''],
      role: ['', [Validators.required]]
    });

    this.setPasswordValidators(true);
  }

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.clearMessages();
    this.loading = true;

    this.userService
      .getAllUsers()
      .pipe(finalize(() => (this.loading = false)))
      .subscribe({
        next: (users) => {
          this.users = users;
          console.log('[AdminUsers] Users loaded', users);
        },
        error: (error: Error) => {
          this.errorMessage = error.message || 'Unable to load users.';
        }
      });
  }

  openCreateForm(): void {
    this.clearMessages();
    this.isEditMode = false;
    this.selectedUserId = null;
    this.formVisible = true;
    this.setPasswordValidators(true);
    this.userForm.reset({
      fullName: '',
      email: '',
      password: '',
      phone: '',
      address: '',
      role: ''
    });
  }

  openEditForm(user: User): void {
    this.clearMessages();
    this.loading = true;

    this.userService
      .getUserById(user.id)
      .pipe(finalize(() => (this.loading = false)))
      .subscribe({
        next: (selectedUser) => {
          this.isEditMode = true;
          this.selectedUserId = selectedUser.id;
          this.formVisible = true;
          this.setPasswordValidators(false);
          this.userForm.reset({
            fullName: selectedUser.fullName || '',
            email: selectedUser.email || '',
            password: '',
            phone: selectedUser.phone || '',
            address: selectedUser.address || '',
            role: selectedUser.role || ''
          });
        },
        error: (error: Error) => {
          this.errorMessage = error.message || 'Unable to load this user.';
        }
      });
  }

  submitUser(): void {
    this.clearMessages();

    if (this.userForm.invalid) {
      this.userForm.markAllAsTouched();
      return;
    }

    const formValue = this.userForm.getRawValue();
    const payloadBase = {
      fullName: formValue.fullName.trim(),
      email: formValue.email.trim(),
      phone: formValue.phone.trim(),
      address: formValue.address.trim(),
      role: formValue.role.trim()
    };

    this.submitting = true;

    if (this.isEditMode && this.selectedUserId !== null) {
      const updatePayload: Partial<User> = {
        ...payloadBase
      };

      if (formValue.password.trim()) {
        updatePayload.password = formValue.password.trim();
      }

      this.userService
        .updateUser(this.selectedUserId, updatePayload)
        .pipe(finalize(() => (this.submitting = false)))
        .subscribe({
          next: () => {
            this.successMessage = 'User updated successfully.';
            this.afterSuccessfulSubmit();
          },
          error: (error: Error) => {
            this.errorMessage = error.message || 'Unable to update user.';
          }
        });

      return;
    }

    const createPayload: Omit<User, 'id'> = {
      ...payloadBase,
      password: formValue.password.trim()
    };

    this.userService
      .addUser(createPayload)
      .pipe(finalize(() => (this.submitting = false)))
      .subscribe({
        next: () => {
          this.successMessage = 'User created successfully.';
          this.afterSuccessfulSubmit();
        },
        error: (error: Error) => {
          this.errorMessage = error.message || 'Unable to create user.';
        }
      });
  }

  confirmDeleteUser(user: User): void {
    this.clearMessages();

    const confirmed = confirm(`Delete user ${user.fullName}?`);
    if (!confirmed) {
      return;
    }

    this.loading = true;
    this.userService
      .deleteUser(user.id)
      .pipe(finalize(() => (this.loading = false)))
      .subscribe({
        next: () => {
          this.successMessage = 'User deleted successfully.';
          this.loadUsers();
        },
        error: (error: Error) => {
          this.errorMessage = error.message || 'Unable to delete user.';
        }
      });
  }

  cancelForm(): void {
    this.formVisible = false;
    this.isEditMode = false;
    this.selectedUserId = null;
    this.setPasswordValidators(true);
    this.userForm.reset({
      fullName: '',
      email: '',
      password: '',
      phone: '',
      address: '',
      role: ''
    });
    this.clearMessages();
  }

  isInvalid(controlName: keyof UserForm, errorCode?: string): boolean {
    const control = this.userForm.controls[controlName];
    if (errorCode) {
      return control.touched && control.hasError(errorCode);
    }

    return control.touched && control.invalid;
  }

  trackByUserId(_: number, user: User): number {
    return user.id;
  }

  private afterSuccessfulSubmit(): void {
    this.formVisible = false;
    this.isEditMode = false;
    this.selectedUserId = null;
    this.setPasswordValidators(true);
    this.userForm.reset({
      fullName: '',
      email: '',
      password: '',
      phone: '',
      address: '',
      role: ''
    });
    this.loadUsers();
  }

  private clearMessages(): void {
    this.successMessage = '';
    this.errorMessage = '';
  }

  private setPasswordValidators(isCreateMode: boolean): void {
    const passwordControl = this.userForm.controls.password;
    if (isCreateMode) {
      passwordControl.setValidators([Validators.required]);
    } else {
      passwordControl.clearValidators();
    }

    passwordControl.updateValueAndValidity();
  }
}
