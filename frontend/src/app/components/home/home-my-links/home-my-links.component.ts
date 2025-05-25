import { Component, OnInit } from '@angular/core';
import { genericPage } from '../../../interfaces/generic-page.interface';
import { Url } from '../../../interfaces/url-service/url-response.interface';
import { UrlService } from '../../../services/url.service';
import { map, take } from 'rxjs';

@Component({
  selector: 'app-home-my-links',
  standalone: false,
  templateUrl: './home-my-links.component.html',
  styleUrl: './home-my-links.component.scss'
})
export class HomeMyLinksComponent implements OnInit {

  page: genericPage<Url> | undefined;

  currentPage: number = 0;

  constructor(private readonly url: UrlService) { }

  ngOnInit(): void {
    this.handlePage();
  }

  prev(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.handlePage();
    }
  }

  next(): void {
    if (this.page !== undefined && this.currentPage < this.page.totalPages) {
      this.currentPage++;
      this.handlePage();
    }
  }

  goTo(page: number) {
    if (page === this.currentPage) return;
    this.currentPage = page;
    this.handlePage();
  }

  handlePage() {
    this.url.getUrls(this.currentPage).pipe(take(1), map(page => {
      const origin = window.location.origin;
      page.content.forEach(l => {
        const code = l.shortenedUrl;
        l.shortenedUrl = `${origin}/g/${code}`
      });
      return page;
    }))
      .subscribe(response => this.page = response);
  }
}
