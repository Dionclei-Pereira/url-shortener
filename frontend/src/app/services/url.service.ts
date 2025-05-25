import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Url } from '../interfaces/url-service/url-response.interface';
import { HttpClient } from '@angular/common/http';
import { genericPage } from '../interfaces/generic-page.interface';

@Injectable({
    providedIn: 'root'
})
export class UrlService {

    private readonly apiUrl = '/api/url'

    constructor(private readonly http: HttpClient) {}

    generateUrl(url: { originalUrl: string }): Observable<Url> {
        return this.http.post<Url>(this.apiUrl, url);
    }

    getUrls(page: number): Observable<genericPage<Url>> {
        return this.http.get<genericPage<Url>>(this.apiUrl, {
            params: { 'page': page.toString() }
        })
    }
}