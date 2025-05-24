import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthComponent } from './auth/auth-component/auth-component.component';
import { HomeComponent } from './home/home-component/home-component.component';
import { AuthRegisterComponent } from './auth/auth-register/auth-register.component';
import { EyeToggleDirective } from './auth/directives/eye-toggle.directive';

@NgModule({
    declarations: [
        AuthComponent,
        HomeComponent,
        AuthRegisterComponent
    ],
    imports: [ 
        CommonModule,
        EyeToggleDirective
    ],
    exports: [
        AuthComponent,
        HomeComponent
    ],
    providers: [],
})
export class ComponentsModule {}