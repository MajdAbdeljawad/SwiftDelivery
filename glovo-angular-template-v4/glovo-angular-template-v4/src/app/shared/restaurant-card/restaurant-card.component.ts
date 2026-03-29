import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Restaurant } from '../../core/models/restaurant.model';

@Component({
  selector: 'app-restaurant-card',
  templateUrl: './restaurant-card.component.html',
  styleUrls: ['./restaurant-card.component.css']
})
export class RestaurantCardComponent {
  @Input({ required: true }) restaurant!: Restaurant;
  @Output() add = new EventEmitter<Restaurant>();

  addToCart(): void {
    this.add.emit(this.restaurant);
  }
}
