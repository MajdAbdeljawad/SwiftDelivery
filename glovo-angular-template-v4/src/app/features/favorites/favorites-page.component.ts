import { Component, OnInit } from '@angular/core';
import { Restaurant } from '../../core/models/restaurant.model';
import { RestaurantService } from '../../core/services/restaurant.service';

@Component({
  selector: 'app-favorites-page',
  templateUrl: './favorites-page.component.html',
  styleUrls: ['./favorites-page.component.css']
})
export class FavoritesPageComponent implements OnInit {
  favorites: Restaurant[] = [];

  constructor(private readonly restaurantService: RestaurantService) {}

  ngOnInit(): void {
    this.favorites = this.restaurantService.getRestaurants().slice(0, 3);
  }
}
