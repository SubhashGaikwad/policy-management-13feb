import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ParameterType } from 'app/entities/enumerations/parameter-type.model';
import { IParameterLookup, ParameterLookup } from '../parameter-lookup.model';

import { ParameterLookupService } from './parameter-lookup.service';

describe('ParameterLookup Service', () => {
  let service: ParameterLookupService;
  let httpMock: HttpTestingController;
  let elemDefault: IParameterLookup;
  let expectedResult: IParameterLookup | IParameterLookup[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ParameterLookupService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      name: 0,
      type: ParameterType.MAKE,
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

    it('should create a ParameterLookup', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ParameterLookup()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ParameterLookup', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 1,
          type: 'BBBBBB',
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

    it('should partial update a ParameterLookup', () => {
      const patchObject = Object.assign(
        {
          type: 'BBBBBB',
          lastModified: 'BBBBBB',
        },
        new ParameterLookup()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ParameterLookup', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 1,
          type: 'BBBBBB',
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

    it('should delete a ParameterLookup', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addParameterLookupToCollectionIfMissing', () => {
      it('should add a ParameterLookup to an empty array', () => {
        const parameterLookup: IParameterLookup = { id: 123 };
        expectedResult = service.addParameterLookupToCollectionIfMissing([], parameterLookup);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(parameterLookup);
      });

      it('should not add a ParameterLookup to an array that contains it', () => {
        const parameterLookup: IParameterLookup = { id: 123 };
        const parameterLookupCollection: IParameterLookup[] = [
          {
            ...parameterLookup,
          },
          { id: 456 },
        ];
        expectedResult = service.addParameterLookupToCollectionIfMissing(parameterLookupCollection, parameterLookup);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ParameterLookup to an array that doesn't contain it", () => {
        const parameterLookup: IParameterLookup = { id: 123 };
        const parameterLookupCollection: IParameterLookup[] = [{ id: 456 }];
        expectedResult = service.addParameterLookupToCollectionIfMissing(parameterLookupCollection, parameterLookup);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(parameterLookup);
      });

      it('should add only unique ParameterLookup to an array', () => {
        const parameterLookupArray: IParameterLookup[] = [{ id: 123 }, { id: 456 }, { id: 89589 }];
        const parameterLookupCollection: IParameterLookup[] = [{ id: 123 }];
        expectedResult = service.addParameterLookupToCollectionIfMissing(parameterLookupCollection, ...parameterLookupArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const parameterLookup: IParameterLookup = { id: 123 };
        const parameterLookup2: IParameterLookup = { id: 456 };
        expectedResult = service.addParameterLookupToCollectionIfMissing([], parameterLookup, parameterLookup2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(parameterLookup);
        expect(expectedResult).toContain(parameterLookup2);
      });

      it('should accept null and undefined values', () => {
        const parameterLookup: IParameterLookup = { id: 123 };
        expectedResult = service.addParameterLookupToCollectionIfMissing([], null, parameterLookup, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(parameterLookup);
      });

      it('should return initial array if no ParameterLookup is added', () => {
        const parameterLookupCollection: IParameterLookup[] = [{ id: 123 }];
        expectedResult = service.addParameterLookupToCollectionIfMissing(parameterLookupCollection, undefined, null);
        expect(expectedResult).toEqual(parameterLookupCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
