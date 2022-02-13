import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPremiunDetails, PremiunDetails } from '../premiun-details.model';

import { PremiunDetailsService } from './premiun-details.service';

describe('PremiunDetails Service', () => {
  let service: PremiunDetailsService;
  let httpMock: HttpTestingController;
  let elemDefault: IPremiunDetails;
  let expectedResult: IPremiunDetails | IPremiunDetails[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PremiunDetailsService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      premium: 0,
      otherLoading: 0,
      otherDiscount: 0,
      addOnPremium: 0,
      liabilityPremium: 0,
      odPremium: 0,
      personalAccidentDiscount: false,
      personalAccident: 0,
      grossPremium: 0,
      gst: 0,
      netPremium: 0,
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

    it('should create a PremiunDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new PremiunDetails()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PremiunDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          premium: 1,
          otherLoading: 1,
          otherDiscount: 1,
          addOnPremium: 1,
          liabilityPremium: 1,
          odPremium: 1,
          personalAccidentDiscount: true,
          personalAccident: 1,
          grossPremium: 1,
          gst: 1,
          netPremium: 1,
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

    it('should partial update a PremiunDetails', () => {
      const patchObject = Object.assign(
        {
          premium: 1,
          otherLoading: 1,
          personalAccidentDiscount: true,
          gst: 1,
          netPremium: 1,
          lastModified: 'BBBBBB',
        },
        new PremiunDetails()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PremiunDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          premium: 1,
          otherLoading: 1,
          otherDiscount: 1,
          addOnPremium: 1,
          liabilityPremium: 1,
          odPremium: 1,
          personalAccidentDiscount: true,
          personalAccident: 1,
          grossPremium: 1,
          gst: 1,
          netPremium: 1,
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

    it('should delete a PremiunDetails', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPremiunDetailsToCollectionIfMissing', () => {
      it('should add a PremiunDetails to an empty array', () => {
        const premiunDetails: IPremiunDetails = { id: 123 };
        expectedResult = service.addPremiunDetailsToCollectionIfMissing([], premiunDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(premiunDetails);
      });

      it('should not add a PremiunDetails to an array that contains it', () => {
        const premiunDetails: IPremiunDetails = { id: 123 };
        const premiunDetailsCollection: IPremiunDetails[] = [
          {
            ...premiunDetails,
          },
          { id: 456 },
        ];
        expectedResult = service.addPremiunDetailsToCollectionIfMissing(premiunDetailsCollection, premiunDetails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PremiunDetails to an array that doesn't contain it", () => {
        const premiunDetails: IPremiunDetails = { id: 123 };
        const premiunDetailsCollection: IPremiunDetails[] = [{ id: 456 }];
        expectedResult = service.addPremiunDetailsToCollectionIfMissing(premiunDetailsCollection, premiunDetails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(premiunDetails);
      });

      it('should add only unique PremiunDetails to an array', () => {
        const premiunDetailsArray: IPremiunDetails[] = [{ id: 123 }, { id: 456 }, { id: 70986 }];
        const premiunDetailsCollection: IPremiunDetails[] = [{ id: 123 }];
        expectedResult = service.addPremiunDetailsToCollectionIfMissing(premiunDetailsCollection, ...premiunDetailsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const premiunDetails: IPremiunDetails = { id: 123 };
        const premiunDetails2: IPremiunDetails = { id: 456 };
        expectedResult = service.addPremiunDetailsToCollectionIfMissing([], premiunDetails, premiunDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(premiunDetails);
        expect(expectedResult).toContain(premiunDetails2);
      });

      it('should accept null and undefined values', () => {
        const premiunDetails: IPremiunDetails = { id: 123 };
        expectedResult = service.addPremiunDetailsToCollectionIfMissing([], null, premiunDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(premiunDetails);
      });

      it('should return initial array if no PremiunDetails is added', () => {
        const premiunDetailsCollection: IPremiunDetails[] = [{ id: 123 }];
        expectedResult = service.addPremiunDetailsToCollectionIfMissing(premiunDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(premiunDetailsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
