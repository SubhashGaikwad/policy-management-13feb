import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProductDetails, getProductDetailsIdentifier } from '../product-details.model';

export type EntityResponseType = HttpResponse<IProductDetails>;
export type EntityArrayResponseType = HttpResponse<IProductDetails[]>;

@Injectable({ providedIn: 'root' })
export class ProductDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/product-details');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(productDetails: IProductDetails): Observable<EntityResponseType> {
    return this.http.post<IProductDetails>(this.resourceUrl, productDetails, { observe: 'response' });
  }

  update(productDetails: IProductDetails): Observable<EntityResponseType> {
    return this.http.put<IProductDetails>(`${this.resourceUrl}/${getProductDetailsIdentifier(productDetails) as number}`, productDetails, {
      observe: 'response',
    });
  }

  partialUpdate(productDetails: IProductDetails): Observable<EntityResponseType> {
    return this.http.patch<IProductDetails>(
      `${this.resourceUrl}/${getProductDetailsIdentifier(productDetails) as number}`,
      productDetails,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProductDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductDetails[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addProductDetailsToCollectionIfMissing(
    productDetailsCollection: IProductDetails[],
    ...productDetailsToCheck: (IProductDetails | null | undefined)[]
  ): IProductDetails[] {
    const productDetails: IProductDetails[] = productDetailsToCheck.filter(isPresent);
    if (productDetails.length > 0) {
      const productDetailsCollectionIdentifiers = productDetailsCollection.map(
        productDetailsItem => getProductDetailsIdentifier(productDetailsItem)!
      );
      const productDetailsToAdd = productDetails.filter(productDetailsItem => {
        const productDetailsIdentifier = getProductDetailsIdentifier(productDetailsItem);
        if (productDetailsIdentifier == null || productDetailsCollectionIdentifiers.includes(productDetailsIdentifier)) {
          return false;
        }
        productDetailsCollectionIdentifiers.push(productDetailsIdentifier);
        return true;
      });
      return [...productDetailsToAdd, ...productDetailsCollection];
    }
    return productDetailsCollection;
  }
}
