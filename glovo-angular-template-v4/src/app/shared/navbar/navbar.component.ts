import { Component, OnDestroy, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { Subject, filter, takeUntil } from 'rxjs';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit, OnDestroy {
  private readonly destroy$ = new Subject<void>();

  menuOpen = false;
  isAdminRoute = false;

  readonly adminLinks = [
    { label: 'Dashboard', path: '/admin', exact: true },
    { label: 'Users', path: '/admin/users', exact: true },
    { label: 'Restaurants', path: '/admin/restaurants', exact: true },
    { label: 'Orders', path: '/admin/orders', exact: true },
    { label: 'Payments', path: '/admin/payments', exact: true }
  ];

  constructor(private readonly router: Router) {}

  ngOnInit(): void {
    this.updateRouteMode(this.router.url);

    this.router.events
      .pipe(
        filter((event): event is NavigationEnd => event instanceof NavigationEnd),
        takeUntil(this.destroy$)
      )
      .subscribe((event) => {
        this.updateRouteMode(event.urlAfterRedirects);
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  private updateRouteMode(url: string): void {
    this.isAdminRoute = url.startsWith('/admin');
  }
}
