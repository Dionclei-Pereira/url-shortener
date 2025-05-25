import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthComponent } from './auth/auth-component/auth-component.component';
import { HomeComponent } from './home/home-component/home-component.component';
import { AuthRegisterComponent } from './auth/auth-register/auth-register.component';
import { EyeToggleDirective } from './auth/directives/eye-toggle.directive';
import { FormsModule } from '@angular/forms';
import { HomeGenerateLinkComponent } from './home/home-generate-link/home-generate-link.component';
import { HomeMyLinksComponent } from './home/home-my-links/home-my-links.component';
import { RedirectComponent } from './redirect/redirect.component';
import { NotFoundComponent } from './not-found/not-found.component';

@NgModule({
    declarations: [
        AuthComponent,
        HomeComponent,
        AuthRegisterComponent,
        HomeGenerateLinkComponent,
        HomeMyLinksComponent,
        RedirectComponent,
        NotFoundComponent
    ],
    imports: [ 
        CommonModule,
        EyeToggleDirective,
        FormsModule
    ],
    exports: [
        AuthComponent,
        HomeComponent,
        HomeGenerateLinkComponent,
        AuthRegisterComponent,
        HomeMyLinksComponent
    ],
    providers: [],
})
export class ComponentsModule {}