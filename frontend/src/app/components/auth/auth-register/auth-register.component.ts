import { Component } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { Router } from '@angular/router';
import { catchError, of, take } from 'rxjs';

@Component({
  selector: 'app-auth-register',
  standalone: false,
  templateUrl: './auth-register.component.html',
  styleUrl: './auth-register.component.scss'
})
export class AuthRegisterComponent {

  errs: String[] = [];

  constructor(private readonly auth: AuthService, private readonly router: Router) {}

  onRegister(name: string, email: string, password: string, confirmPassword: string): void {
    this.errs = [];
    if (password !== confirmPassword) {
      this.errs.push("Passwords do not match");
      return;
    }
    this.auth.register({ email: email, password: password, name: name })
      .pipe(take(1)).subscribe({
        
        next: (response) => {
          if (response) {
            this.router.navigate(['auth/login']);
          }
        },

        error: (err) => {
          const rawMessage: string = err.error?.message ?? '';
          const errs = rawMessage.replace('[', '').replace(']', '').split(',');
          errs.forEach(e => this.errs.push(e.trim()));
        }
      })
  }

  onLogin(): void {
    this.router.navigate(['auth/login'])
  }
}
