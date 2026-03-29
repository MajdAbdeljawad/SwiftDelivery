import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Restaurant } from '../../../core/models/restaurant.model';
import { MenuItem } from '../../../core/models/menu-item.model';
import { CartService } from '../../../core/services/cart.service';
import { MenuService } from '../../../core/services/menu.service';
import { RestaurantService } from '../../../core/services/restaurant.service';

@Component({
  selector: 'app-restaurant-details',
  templateUrl: './restaurant-details.component.html',
  styleUrls: ['./restaurant-details.component.css']
})
export class RestaurantDetailsComponent implements OnInit {
  restaurant?: Restaurant;
  menu: MenuItem[] = [];

  constructor(
    private readonly route: ActivatedRoute,
    private readonly restaurantService: RestaurantService,
    private readonly menuService: MenuService,
    private readonly cartService: CartService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.restaurant = this.restaurantService.getRestaurants().find((entry) => entry.id === id);
    this.menu = this.menuService.getByRestaurant(id);
  }

  addToCart(item: MenuItem): void {
    this.cartService.add(item);
  }
}
