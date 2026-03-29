import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { appRoutes } from './app.routes';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { FooterComponent } from './shared/footer/footer.component';
import { SearchBarComponent } from './shared/search-bar/search-bar.component';
import { RestaurantCardComponent } from './shared/restaurant-card/restaurant-card.component';
import { HomeComponent } from './features/home/home.component';
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

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    FooterComponent,
    SearchBarComponent,
    RestaurantCardComponent,
    HomeComponent,
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
  imports: [BrowserModule, HttpClientModule, FormsModule, ReactiveFormsModule, RouterModule.forRoot(appRoutes)],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
