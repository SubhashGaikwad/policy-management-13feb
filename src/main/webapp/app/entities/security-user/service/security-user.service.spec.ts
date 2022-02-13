import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISecurityUser, SecurityUser } from '../security-user.model';

import { SecurityUserService } from './security-user.service';

describe('SecurityUser Service', () => {
  let service: SecurityUserService;
  let httpMock: HttpTestingController;
  let elemDefault: ISecurityUser;
  let expectedResult: ISecurityUser | ISecurityUser[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SecurityUserService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      firstName: 'AAAAAAA',
      lastName: 'AAAAAAA',
      designation: 'AAAAAAA',
      login: 'AAAAAAA',
      passwordHash: 'AAAAAAA',
      email: 'AAAAAAA',
      imageUrl: 'AAAAAAA',
      activated: false,
      langKey: 'AAAAAAA',
      activationKey: 'AAAAAAA',
      resetKey: 'AAAAAAA',
      resetDate: 'AAAAAAA',
      mobileNo: 'AAAAAAA',
      oneTimePassword: 'AAAAAAA',
      otpExpiryTime: 'AAAAAAA',
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

    it('should create a SecurityUser', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new SecurityUser()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SecurityUser', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          firstName: 'BBBBBB',
          lastName: 'BBBBBB',
          designation: 'BBBBBB',
          login: 'BBBBBB',
          passwordHash: 'BBBBBB',
          email: 'BBBBBB',
          imageUrl: 'BBBBBB',
          activated: true,
          langKey: 'BBBBBB',
          activationKey: 'BBBBBB',
          resetKey: 'BBBBBB',
          resetDate: 'BBBBBB',
          mobileNo: 'BBBBBB',
          oneTimePassword: 'BBBBBB',
          otpExpiryTime: 'BBBBBB',
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

    it('should partial update a SecurityUser', () => {
      const patchObject = Object.assign(
        {
          login: 'BBBBBB',
          email: 'BBBBBB',
          langKey: 'BBBBBB',
          oneTimePassword: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
        },
        new SecurityUser()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SecurityUser', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          firstName: 'BBBBBB',
          lastName: 'BBBBBB',
          designation: 'BBBBBB',
          login: 'BBBBBB',
          passwordHash: 'BBBBBB',
          email: 'BBBBBB',
          imageUrl: 'BBBBBB',
          activated: true,
          langKey: 'BBBBBB',
          activationKey: 'BBBBBB',
          resetKey: 'BBBBBB',
          resetDate: 'BBBBBB',
          mobileNo: 'BBBBBB',
          oneTimePassword: 'BBBBBB',
          otpExpiryTime: 'BBBBBB',
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

    it('should delete a SecurityUser', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSecurityUserToCollectionIfMissing', () => {
      it('should add a SecurityUser to an empty array', () => {
        const securityUser: ISecurityUser = { id: 123 };
        expectedResult = service.addSecurityUserToCollectionIfMissing([], securityUser);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(securityUser);
      });

      it('should not add a SecurityUser to an array that contains it', () => {
        const securityUser: ISecurityUser = { id: 123 };
        const securityUserCollection: ISecurityUser[] = [
          {
            ...securityUser,
          },
          { id: 456 },
        ];
        expectedResult = service.addSecurityUserToCollectionIfMissing(securityUserCollection, securityUser);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SecurityUser to an array that doesn't contain it", () => {
        const securityUser: ISecurityUser = { id: 123 };
        const securityUserCollection: ISecurityUser[] = [{ id: 456 }];
        expectedResult = service.addSecurityUserToCollectionIfMissing(securityUserCollection, securityUser);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(securityUser);
      });

      it('should add only unique SecurityUser to an array', () => {
        const securityUserArray: ISecurityUser[] = [{ id: 123 }, { id: 456 }, { id: 61352 }];
        const securityUserCollection: ISecurityUser[] = [{ id: 123 }];
        expectedResult = service.addSecurityUserToCollectionIfMissing(securityUserCollection, ...securityUserArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const securityUser: ISecurityUser = { id: 123 };
        const securityUser2: ISecurityUser = { id: 456 };
        expectedResult = service.addSecurityUserToCollectionIfMissing([], securityUser, securityUser2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(securityUser);
        expect(expectedResult).toContain(securityUser2);
      });

      it('should accept null and undefined values', () => {
        const securityUser: ISecurityUser = { id: 123 };
        expectedResult = service.addSecurityUserToCollectionIfMissing([], null, securityUser, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(securityUser);
      });

      it('should return initial array if no SecurityUser is added', () => {
        const securityUserCollection: ISecurityUser[] = [{ id: 123 }];
        expectedResult = service.addSecurityUserToCollectionIfMissing(securityUserCollection, undefined, null);
        expect(expectedResult).toEqual(securityUserCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
