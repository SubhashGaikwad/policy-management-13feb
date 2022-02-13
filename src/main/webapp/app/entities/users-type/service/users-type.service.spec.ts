import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IUsersType, UsersType } from '../users-type.model';

import { UsersTypeService } from './users-type.service';

describe('UsersType Service', () => {
  let service: UsersTypeService;
  let httpMock: HttpTestingController;
  let elemDefault: IUsersType;
  let expectedResult: IUsersType | IUsersType[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(UsersTypeService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
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

    it('should create a UsersType', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new UsersType()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a UsersType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
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

    it('should partial update a UsersType', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
        },
        new UsersType()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of UsersType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
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

    it('should delete a UsersType', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addUsersTypeToCollectionIfMissing', () => {
      it('should add a UsersType to an empty array', () => {
        const usersType: IUsersType = { id: 123 };
        expectedResult = service.addUsersTypeToCollectionIfMissing([], usersType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(usersType);
      });

      it('should not add a UsersType to an array that contains it', () => {
        const usersType: IUsersType = { id: 123 };
        const usersTypeCollection: IUsersType[] = [
          {
            ...usersType,
          },
          { id: 456 },
        ];
        expectedResult = service.addUsersTypeToCollectionIfMissing(usersTypeCollection, usersType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a UsersType to an array that doesn't contain it", () => {
        const usersType: IUsersType = { id: 123 };
        const usersTypeCollection: IUsersType[] = [{ id: 456 }];
        expectedResult = service.addUsersTypeToCollectionIfMissing(usersTypeCollection, usersType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(usersType);
      });

      it('should add only unique UsersType to an array', () => {
        const usersTypeArray: IUsersType[] = [{ id: 123 }, { id: 456 }, { id: 86703 }];
        const usersTypeCollection: IUsersType[] = [{ id: 123 }];
        expectedResult = service.addUsersTypeToCollectionIfMissing(usersTypeCollection, ...usersTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const usersType: IUsersType = { id: 123 };
        const usersType2: IUsersType = { id: 456 };
        expectedResult = service.addUsersTypeToCollectionIfMissing([], usersType, usersType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(usersType);
        expect(expectedResult).toContain(usersType2);
      });

      it('should accept null and undefined values', () => {
        const usersType: IUsersType = { id: 123 };
        expectedResult = service.addUsersTypeToCollectionIfMissing([], null, usersType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(usersType);
      });

      it('should return initial array if no UsersType is added', () => {
        const usersTypeCollection: IUsersType[] = [{ id: 123 }];
        expectedResult = service.addUsersTypeToCollectionIfMissing(usersTypeCollection, undefined, null);
        expect(expectedResult).toEqual(usersTypeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
