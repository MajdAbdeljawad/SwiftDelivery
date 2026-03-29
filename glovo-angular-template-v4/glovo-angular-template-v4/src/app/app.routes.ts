import { Routes } from '@angular/router';

import { AdminDashboardComponent } from './features/admin/dashboard/admin-dashboard.component';
import { AdminDeliveriesComponent } from './features/admin/deliveries/admin-deliveries.component';
import { AdminOrdersComponent } from './features/admin/orders/admin-orders.component';
import { AdminPaymentsComponent } from './features/admin/payments/admin-payments.component';
import { AdminProductsComponent } from './features/admin/products/admin-products.component';
import { AdminPromotionsComponent } from './features/admin/promotions/admin-promotions.component';
import { AdminRestaurantsComponent } from './features/admin/restaurants/admin-restaurants.component';
import { AdminUsersComponent } from './features/admin/users/admin-users.component';
import { CartPageComponent } from './features/cart/cart-page.component';
import { CheckoutPageComponent } from './features/checkout/checkout-page.component';
import { FavoritesPageComponent } from './features/favorites/favorites-page.component';
import { HomeComponent } from './features/home/home.component';
import { OrdersPageComponent } from './features/orders/orders-page.component';
import { ProfilePageComponent } from './features/profile/profile-page.component';
import { RestaurantDetailsComponent } from './features/restaurants/details/restaurant-details.component';
import { RestaurantsListComponent } from './features/restaurants/list/restaurants-list.component';

export const appRoutes: Routes = [
  { path: '', component: HomeComponent },
  {
    path: 'login',
    loadComponent: () => import('./features/auth/login/login.component').then((m) => m.LoginComponent)
  },
  {
    path: 'register',
    loadComponent: () => import('./features/auth/register/register.component').then((m) => m.RegisterComponent)
  },
  {
    path: 'dashboard',
    loadComponent: () => import('./features/dashboard/dashboard.component').then((m) => m.DashboardComponent)
  },
  { path: 'restaurants', component: RestaurantsListComponent },
  { path: 'restaurants/:id', component: RestaurantDetailsComponent },
  { path: 'favorites', component: FavoritesPageComponent },
  { path: 'orders', component: OrdersPageComponent },
  { path: 'profile', component: ProfilePageComponent },
  { path: 'cart', component: CartPageComponent },
  { path: 'checkout', component: CheckoutPageComponent },

  { path: 'admin', component: AdminDashboardComponent },
  { path: 'admin/users', component: AdminUsersComponent },
  { path: 'admin/restaurants', component: AdminRestaurantsComponent },
  { path: 'admin/products', component: AdminProductsComponent },
  { path: 'admin/orders', component: AdminOrdersComponent },
  { path: 'admin/payments', component: AdminPaymentsComponent },
  { path: 'admin/deliveries', component: AdminDeliveriesComponent },
  { path: 'admin/promotions', component: AdminPromotionsComponent },

  { path: '**', redirectTo: '' }
];