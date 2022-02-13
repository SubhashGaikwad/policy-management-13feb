import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { INominee, Nominee } from '../nominee.model';

import { NomineeService } from './nominee.service';

describe('Nominee Service', () => {
  let service: NomineeService;
  let httpMock: HttpTestingController;
  let elemDefault: INominee;
  let expectedResult: INominee | INominee[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NomineeService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      name: 0,
      relation: 'AAAAAAA',
      nomineePercentage: 0,
      contactNo: 0,
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

    it('should create a Nominee', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Nominee()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Nominee', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 1,
          relation: 'BBBBBB',
          nomineePercentage: 1,
          contactNo: 1,
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

    it('should partial update a Nominee', () => {
      const patchObject = Object.assign(
        {
          relation: 'BBBBBB',
          nomineePercentage: 1,
        },
        new Nominee()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Nominee', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 1,
          relation: 'BBBBBB',
          nomineePercentage: 1,
          contactNo: 1,
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

    it('should delete a Nominee', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addNomineeToCollectionIfMissing', () => {
      it('should add a Nominee to an empty array', () => {
        const nominee: INominee = { id: 123 };
        expectedResult = service.addNomineeToCollectionIfMissing([], nominee);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(nominee);
      });

      it('should not add a Nominee to an array that contains it', () => {
        const nominee: INominee = { id: 123 };
        const nomineeCollection: INominee[] = [
          {
            ...nominee,
          },
          { id: 456 },
        ];
        expectedResult = service.addNomineeToCollectionIfMissing(nomineeCollection, nominee);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Nominee to an array that doesn't contain it", () => {
        const nominee: INominee = { id: 123 };
        const nomineeCollection: INominee[] = [{ id: 456 }];
        expectedResult = service.addNomineeToCollectionIfMissing(nomineeCollection, nominee);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(nominee);
      });

      it('should add only unique Nominee to an array', () => {
        const nomineeArray: INominee[] = [{ id: 123 }, { id: 456 }, { id: 25243 }];
        const nomineeCollection: INominee[] = [{ id: 123 }];
        expectedResult = service.addNomineeToCollectionIfMissing(nomineeCollection, ...nomineeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const nominee: INominee = { id: 123 };
        const nominee2: INominee = { id: 456 };
        expectedResult = service.addNomineeToCollectionIfMissing([], nominee, nominee2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(nominee);
        expect(expectedResult).toContain(nominee2);
      });

      it('should accept null and undefined values', () => {
        const nominee: INominee = { id: 123 };
        expectedResult = service.addNomineeToCollectionIfMissing([], null, nominee, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(nominee);
      });

      it('should return initial array if no Nominee is added', () => {
        const nomineeCollection: INominee[] = [{ id: 123 }];
        expectedResult = service.addNomineeToCollectionIfMissing(nomineeCollection, undefined, null);
        expect(expectedResult).toEqual(nomineeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
