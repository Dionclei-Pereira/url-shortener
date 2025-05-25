import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UrlService } from '../../services/url.service';
import { take } from 'rxjs';

@Component({
  selector: 'app-redirect',
  standalone: false,
  templateUrl: './redirect.component.html',
  styleUrl: './redirect.component.scss'
})
export class RedirectComponent implements OnInit {
  
  constructor(private readonly route: ActivatedRoute, private readonly url: UrlService, private readonly router: Router) {}

  ngOnInit(): void {
    const code = this.route.snapshot.paramMap.get('code');
    if (code === null) {
      this.router.navigate(['notfound'])
      return;
    }
    this.url.getOriginalUrl(code).pipe(take(1))
      .subscribe({

        next: (response) => {
          window.location.href = response.link;
        },

        error: (err) => {
          console.log(err);
          this.router.navigate(['notfound'])
        }
      })
  }
}
