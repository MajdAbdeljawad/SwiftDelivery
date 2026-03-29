import { Component, OnDestroy, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { Subject, filter, takeUntil } from 'rxjs';

import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit, OnDestroy {
  private readonly destroy$ = new Subject<void>();

  menuOpen = false;
  isAdminRoute = false;
  isAuthenticated = false;
  displayName = '';
  role = '';

  readonly adminLinks = [
    { label: 'Dashboard', path: '/admin', exact: true },
    { label: 'Users', path: '/admin/users', exact: true },
    { label: 'Restaurants', path: '/admin/restaurants', exact: true },
    { label: 'Orders', path: '/admin/orders', exact: true },
    { label: 'Payments', path: '/admin/payments', exact: true }
  ];

  constructor(
    private readonly router: Router,
    private readonly authService: AuthService
  ) {}

  ngOnInit(): void {
    this.updateRouteMode(this.router.url);
    this.refreshAuthUiState();

    this.router.events
      .pipe(
        filter((event): event is NavigationEnd => event instanceof NavigationEnd),
        takeUntil(this.destroy$)
      )
      .subscribe((event) => {
        this.updateRouteMode(event.urlAfterRedirects);
        this.refreshAuthUiState();
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  private updateRouteMode(url: string): void {
    this.isAdminRoute = url.startsWith('/admin');
  }

  signOut(): void {
    this.authService.logout();
    this.refreshAuthUiState();
    this.router.navigate(['/login']);
  }

  private refreshAuthUiState(): void {
    this.isAuthenticated = this.authService.isLoggedIn();
    this.displayName = this.authService.getDisplayName();
    this.role = (this.authService.getRole() || '').toUpperCase();
  }

  get showUserIdentity(): boolean {
    return this.isAuthenticated && this.role !== 'ADMIN';
  }
}
