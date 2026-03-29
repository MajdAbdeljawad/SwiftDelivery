export interface FoodOrder {
  id?: number;
  userId: number;
  restaurantName: string;
  status: string;
  totalPrice: number;
  createdAt?: string;
}
