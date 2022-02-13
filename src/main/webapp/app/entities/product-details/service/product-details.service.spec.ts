import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProductDetails, ProductDetails } from '../product-details.model';

import { ProductDetailsService } from './product-details.service';

describe('ProductDetails Service', () => {
  let service: ProductDetailsService;
  let httpMock: HttpTestingController;
  let elemDefault: IProductDetails;
  let expectedResult: IProductDetails | IProductDetails[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProductDetailsService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      details: 'AAAAAAA',
      featurs: 'AAAAAAA',
      activationDate: 'AAAAAAA',
      lastModified: 'AAAAAAA',
      lastModifiedBy: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a ProductDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ProductDetails()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProductDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          details: 'BBBBBB',
          featurs: 'BBBBBB',
          activationDate: 'BBBBBB',
          lastModified: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProductDetails', () => {
      const patchObject = Object.assign(
        {
          details: 'BBBBBB',
          activationDate: 'BBBBBB',
        },
        new ProductDetails()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProductDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          details: 'BBBBBB',
          featurs: 'BBBBBB',
          activationDate: 'BBBBBB',
          lastModified: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a ProductDetails', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addProductDetailsToCollectionIfMissing', () => {
      it('should add a ProductDetails to an empty array', () => {
        const productDetails: IProductDetails = { id: 123 };
        expectedResult = service.addProductDetailsToCollectionIfMissing([], productDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productDetails);
      });

      it('should not add a ProductDetails to an array that contains it', () => {
        const productDetails: IProductDetails = { id: 123 };
        const productDetailsCollection: IProductDetails[] = [
          {
            ...productDetails,
          },
          { id: 456 },
        ];
        expectedResult = service.addProductDetailsToCollectionIfMissing(productDetailsCollection, productDetails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProductDetails to an array that doesn't contain it", () => {
        const productDetails: IProductDetails = { id: 123 };
        const productDetailsCollection: IProductDetails[] = [{ id: 456 }];
        expectedResult = service.addProductDetailsToCollectionIfMissing(productDetailsCollection, productDetails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productDetails);
      });

      it('should add only unique ProductDetails to an array', () => {
        const productDetailsArray: IProductDetails[] = [{ id: 123 }, { id: 456 }, { id: 77692 }];
        const productDetailsCollection: IProductDetails[] = [{ id: 123 }];
        expectedResult = service.addProductDetailsToCollectionIfMissing(productDetailsCollection, ...productDetailsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const productDetails: IProductDetails = { id: 123 };
        const productDetails2: IProductDetails = { id: 456 };
        expectedResult = service.addProductDetailsToCollectionIfMissing([], productDetails, productDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productDetails);
        expect(expectedResult).toContain(productDetails2);
      });

      it('should accept null and undefined values', () => {
        const productDetails: IProductDetails = { id: 123 };
        expectedResult = service.addProductDetailsToCollectionIfMissing([], null, productDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productDetails);
      });

      it('should return initial array if no ProductDetails is added', () => {
        const productDetailsCollection: IProductDetails[] = [{ id: 123 }];
        expectedResult = service.addProductDetailsToCollectionIfMissing(productDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(productDetailsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
