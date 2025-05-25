import { Component } from '@angular/core';
import { UrlService } from '../../../services/url.service';
import { take } from 'rxjs';

@Component({
  selector: 'app-home-generate-link',
  standalone: false,
  templateUrl: './home-generate-link.component.html',
  styleUrl: './home-generate-link.component.scss'
})
export class HomeGenerateLinkComponent {

  originalUrl: string = ''; 
  generatedUrl: string = ''; 

  constructor(private readonly url: UrlService) {}

  generateUrl() {
    this.url.generateUrl({ originalUrl: this.originalUrl })
      .pipe(take(1)).subscribe(response => {
        const url = window.location.origin;
        this.generatedUrl = `${url}/g/${response.shortenedUrl}`;
      });
  }
}
