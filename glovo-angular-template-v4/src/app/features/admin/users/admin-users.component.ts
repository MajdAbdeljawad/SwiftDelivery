import { Component, OnInit } from '@angular/core';
import { AppUser } from '../../../core/models/app-user.model';
import { UserApiService } from '../../../core/services/user-api.service';

@Component({
  selector: 'app-admin-users',
  templateUrl: './admin-users.component.html',
  styleUrls: ['./admin-users.component.css']
})
export class AdminUsersComponent implements OnInit {
  users: AppUser[] = [];
  filteredUsers: AppUser[] = [];
  searchTerm = '';
  isLoading = false;
  errorMessage = '';

  formUser: AppUser = {
    fullName: '',
    email: '',
    phone: '',
    address: ''
  };

  editId: number | null = null;

  constructor(private readonly userApiService: UserApiService) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.isLoading = true;
    this.errorMessage = '';
    this.userApiService.getAll().subscribe({
      next: (users) => {
        this.users = users;
        this.applyFilter();
        this.isLoading = false;
      },
      error: () => {
        this.errorMessage = 'Impossible de charger les utilisateurs.';
        this.isLoading = false;
      }
    });
  }

  applyFilter(): void {
    const term = this.searchTerm.trim().toLowerCase();
    if (!term) {
      this.filteredUsers = this.users;
      return;
    }

    this.filteredUsers = this.users.filter((user) =>
      [user.fullName, user.email, user.phone, user.address].some((value) =>
        value.toLowerCase().includes(term)
      )
    );
  }

  edit(user: AppUser): void {
    this.editId = user.id ?? null;
    this.formUser = {
      fullName: user.fullName,
      email: user.email,
      phone: user.phone,
      address: user.address
    };
  }

  resetForm(): void {
    this.editId = null;
    this.formUser = {
      fullName: '',
      email: '',
      phone: '',
      address: ''
    };
  }

  save(): void {
    this.errorMessage = '';

    if (this.editId === null) {
      this.userApiService.create(this.formUser).subscribe({
        next: () => {
          this.resetForm();
          this.loadUsers();
        },
        error: () => {
          this.errorMessage = 'Creation utilisateur echouee.';
        }
      });
      return;
    }

    this.userApiService.update(this.editId, this.formUser).subscribe({
      next: () => {
        this.resetForm();
        this.loadUsers();
      },
      error: () => {
        this.errorMessage = 'Mise a jour utilisateur echouee.';
      }
    });
  }

  remove(id: number | undefined): void {
    if (!id) {
      return;
    }

    this.errorMessage = '';
    this.userApiService.delete(id).subscribe({
      next: () => this.loadUsers(),
      error: () => {
        this.errorMessage = 'Suppression utilisateur echouee.';
      }
    });
  }
}
