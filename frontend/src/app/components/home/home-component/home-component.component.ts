import { Component } from '@angular/core';
@Component({
  selector: 'app-home',
  standalone: false,
  templateUrl: './home-component.component.html',
  styleUrl: './home-component.component.scss'
})
export class HomeComponent {

  tab: number = 0;

  onClick() {
    console.log(this.tab);
  }
}
