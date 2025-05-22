import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { map, Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable({
    providedIn: 'root'
})
export class AuthGuard implements CanActivate {

    constructor(private readonly auth: AuthService, private readonly router: Router) {}

    canActivate(): Observable<boolean> {
        return this.auth.isLoggedIn().pipe(
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
