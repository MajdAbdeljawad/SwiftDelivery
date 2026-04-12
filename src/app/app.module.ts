import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { FooterComponent } from './shared/footer/footer.component';
import { SearchBarComponent } from './shared/search-bar/search-bar.component';
import { RestaurantCardComponent } from './shared/restaurant-card/restaurant-card.component';
import { HomeComponent } from './features/home/home.component';
import { LoginComponent } from './features/auth/login/login.component';
import { RegisterComponent } from './features/auth/register/register.component';
import { RestaurantsListComponent } from './features/restaurants/list/restaurants-list.component';
import { RestaurantDetailsComponent } from './features/restaurants/details/restaurant-details.component';
import { CartPageComponent } from './features/cart/cart-page.component';
import { CheckoutPageComponent } from './features/checkout/checkout-page.component';
import { OrdersPageComponent } from './features/orders/orders-page.component';
import { ProfilePageComponent } from './features/profile/profile-page.component';
import { FavoritesPageComponent } from './features/favorites/favorites-page.component';

import { AdminDashboardComponent } from './features/admin/dashboard/admin-dashboard.component';
import { AdminUsersComponent } from './features/admin/users/admin-users.component';
import { AdminRestaurantsComponent } from './features/admin/restaurants/admin-restaurants.component';
import { AdminProductsComponent } from './features/admin/products/admin-products.component';
import { AdminOrdersComponent } from './features/admin/orders/admin-orders.component';
import { AdminPaymentsComponent } from './features/admin/payments/admin-payments.component';
import { AdminDeliveriesComponent } from './features/admin/deliveries/admin-deliveries.component';
import { AdminPromotionsComponent } from './features/admin/promotions/admin-promotions.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
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

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    FooterComponent,
    SearchBarComponent,
    RestaurantCardComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    RestaurantsListComponent,
    RestaurantDetailsComponent,
    CartPageComponent,
    CheckoutPageComponent,
    OrdersPageComponent,
    ProfilePageComponent,
    FavoritesPageComponent,
    AdminDashboardComponent,
    AdminUsersComponent,
    AdminRestaurantsComponent,
    AdminProductsComponent,
    AdminOrdersComponent,
    AdminPaymentsComponent,
    AdminDeliveriesComponent,
    AdminPromotionsComponent
  ],
  imports: [BrowserModule, FormsModule, HttpClientModule, RouterModule.forRoot(routes)],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
