import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRider, Rider } from '../rider.model';

import { RiderService } from './rider.service';

describe('Rider Service', () => {
  let service: RiderService;
  let httpMock: HttpTestingController;
  let elemDefault: IRider;
  let expectedResult: IRider | IRider[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RiderService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
      commDate: 'AAAAAAA',
      sum: 'AAAAAAA',
      term: 'AAAAAAA',
      ppt: 'AAAAAAA',
      premium: 0,
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

    it('should create a Rider', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Rider()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Rider', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          commDate: 'BBBBBB',
          sum: 'BBBBBB',
          term: 'BBBBBB',
          ppt: 'BBBBBB',
          premium: 1,
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

    it('should partial update a Rider', () => {
      const patchObject = Object.assign(
        {
          commDate: 'BBBBBB',
          sum: 'BBBBBB',
          ppt: 'BBBBBB',
          premium: 1,
        },
        new Rider()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Rider', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          commDate: 'BBBBBB',
          sum: 'BBBBBB',
          term: 'BBBBBB',
          ppt: 'BBBBBB',
          premium: 1,
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

    it('should delete a Rider', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addRiderToCollectionIfMissing', () => {
      it('should add a Rider to an empty array', () => {
        const rider: IRider = { id: 123 };
        expectedResult = service.addRiderToCollectionIfMissing([], rider);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(rider);
      });

      it('should not add a Rider to an array that contains it', () => {
        const rider: IRider = { id: 123 };
        const riderCollection: IRider[] = [
          {
            ...rider,
          },
          { id: 456 },
        ];
        expectedResult = service.addRiderToCollectionIfMissing(riderCollection, rider);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Rider to an array that doesn't contain it", () => {
        const rider: IRider = { id: 123 };
        const riderCollection: IRider[] = [{ id: 456 }];
        expectedResult = service.addRiderToCollectionIfMissing(riderCollection, rider);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(rider);
      });

      it('should add only unique Rider to an array', () => {
        const riderArray: IRider[] = [{ id: 123 }, { id: 456 }, { id: 3646 }];
        const riderCollection: IRider[] = [{ id: 123 }];
        expectedResult = service.addRiderToCollectionIfMissing(riderCollection, ...riderArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const rider: IRider = { id: 123 };
        const rider2: IRider = { id: 456 };
        expectedResult = service.addRiderToCollectionIfMissing([], rider, rider2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(rider);
        expect(expectedResult).toContain(rider2);
      });

      it('should accept null and undefined values', () => {
        const rider: IRider = { id: 123 };
        expectedResult = service.addRiderToCollectionIfMissing([], null, rider, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(rider);
      });

      it('should return initial array if no Rider is added', () => {
        const riderCollection: IRider[] = [{ id: 123 }];
        expectedResult = service.addRiderToCollectionIfMissing(riderCollection, undefined, null);
        expect(expectedResult).toEqual(riderCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
