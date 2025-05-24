import { Directive, HostBinding, HostListener, Input, ViewChild } from '@angular/core';

@Directive({
    selector: '[appEyeToggle]',
})
export class EyeToggleDirective {

    @HostBinding('src')
    img = 'icons/hidden.png';

    @Input()
    input!: HTMLInputElement;

    @HostListener('click')
    toggleEye() {
        if (this.input.type === 'text') {
            this.input.type = 'password';
            this.img = 'icons/hidden.png'
            return;
        }
        this.input.type = 'text';
        this.img = 'icons/eye.png'
    }
}