import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.css']
})
export class SearchBarComponent {
  query = '';

  @Output() search = new EventEmitter<string>();

  submit(): void {
    this.search.emit(this.query.trim());
  }
}
