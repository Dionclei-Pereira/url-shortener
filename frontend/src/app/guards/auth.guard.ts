import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { catchError, map, Observable, of } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable({
    providedIn: 'root'
})
export class AuthGuard implements CanActivate {

    constructor(private readonly auth: AuthService, private readonly router: Router) {}

    canActivate(): Observable<boolean> {
        return this.auth.isLoggedIn().pipe(
            catchError(() => {
                this.router.navigate(['/auth']);
                localStorage.removeItem('token');
                return of(false);
            }),
            map(isValid => {
                if (!isValid) {
                    this.router.navigate(['/auth']);
                    return false;
                }
                return true;
            })
        )
    }
}
