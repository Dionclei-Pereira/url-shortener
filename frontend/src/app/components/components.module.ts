import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthComponent } from './auth/auth-component/auth-component.component';
import { HomeComponent } from './home/home-component/home-component.component';

@NgModule({
    declarations: [
        AuthComponent,
        HomeComponent
    ],
    imports: [ CommonModule ],
    exports: [
        AuthComponent,
        HomeComponent
    ],
    providers: [],
})
export class ComponentsModule {}