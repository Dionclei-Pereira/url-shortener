import { Component } from '@angular/core';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-home',
  standalone: false,
  templateUrl: './home-component.component.html',
  styleUrl: './home-component.component.scss'
})
export class HomeComponent {

  constructor(private auth: AuthService) {}

  clear() {
    this.auth.logout();
  }

}
