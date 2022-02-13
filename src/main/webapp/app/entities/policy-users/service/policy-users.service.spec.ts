import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { StatusInd } from 'app/entities/enumerations/status-ind.model';
import { IPolicyUsers, PolicyUsers } from '../policy-users.model';

import { PolicyUsersService } from './policy-users.service';

describe('PolicyUsers Service', () => {
  let service: PolicyUsersService;
  let httpMock: HttpTestingController;
  let elemDefault: IPolicyUsers;
  let expectedResult: IPolicyUsers | IPolicyUsers[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PolicyUsersService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      groupCode: 'AAAAAAA',
      groupHeadName: 'AAAAAAA',
      firstName: 'AAAAAAA',
      lastName: 'AAAAAAA',
      birthDate: 'AAAAAAA',
      marriageDate: 'AAAAAAA',
      userTypeId: 0,
      username: 'AAAAAAA',
      password: 'AAAAAAA',
      email: 'AAAAAAA',
      imageUrl: 'AAAAAAA',
      status: StatusInd.A,
      activated: false,
      licenceExpiryDate: 'AAAAAAA',
      mobileNo: 'AAAAAAA',
      aadharCardNuber: 'AAAAAAA',
      pancardNumber: 'AAAAAAA',
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

    it('should create a PolicyUsers', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new PolicyUsers()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PolicyUsers', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          groupCode: 'BBBBBB',
          groupHeadName: 'BBBBBB',
          firstName: 'BBBBBB',
          lastName: 'BBBBBB',
          birthDate: 'BBBBBB',
          marriageDate: 'BBBBBB',
          userTypeId: 1,
          username: 'BBBBBB',
          password: 'BBBBBB',
          email: 'BBBBBB',
          imageUrl: 'BBBBBB',
          status: 'BBBBBB',
          activated: true,
          licenceExpiryDate: 'BBBBBB',
          mobileNo: 'BBBBBB',
          aadharCardNuber: 'BBBBBB',
          pancardNumber: 'BBBBBB',
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

    it('should partial update a PolicyUsers', () => {
      const patchObject = Object.assign(
        {
          groupCode: 'BBBBBB',
          password: 'BBBBBB',
          email: 'BBBBBB',
          imageUrl: 'BBBBBB',
          status: 'BBBBBB',
          activated: true,
          licenceExpiryDate: 'BBBBBB',
          mobileNo: 'BBBBBB',
          aadharCardNuber: 'BBBBBB',
          oneTimePassword: 'BBBBBB',
        },
        new PolicyUsers()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PolicyUsers', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          groupCode: 'BBBBBB',
          groupHeadName: 'BBBBBB',
          firstName: 'BBBBBB',
          lastName: 'BBBBBB',
          birthDate: 'BBBBBB',
          marriageDate: 'BBBBBB',
          userTypeId: 1,
          username: 'BBBBBB',
          password: 'BBBBBB',
          email: 'BBBBBB',
          imageUrl: 'BBBBBB',
          status: 'BBBBBB',
          activated: true,
          licenceExpiryDate: 'BBBBBB',
          mobileNo: 'BBBBBB',
          aadharCardNuber: 'BBBBBB',
          pancardNumber: 'BBBBBB',
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

    it('should delete a PolicyUsers', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPolicyUsersToCollectionIfMissing', () => {
      it('should add a PolicyUsers to an empty array', () => {
        const policyUsers: IPolicyUsers = { id: 123 };
        expectedResult = service.addPolicyUsersToCollectionIfMissing([], policyUsers);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(policyUsers);
      });

      it('should not add a PolicyUsers to an array that contains it', () => {
        const policyUsers: IPolicyUsers = { id: 123 };
        const policyUsersCollection: IPolicyUsers[] = [
          {
            ...policyUsers,
          },
          { id: 456 },
        ];
        expectedResult = service.addPolicyUsersToCollectionIfMissing(policyUsersCollection, policyUsers);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PolicyUsers to an array that doesn't contain it", () => {
        const policyUsers: IPolicyUsers = { id: 123 };
        const policyUsersCollection: IPolicyUsers[] = [{ id: 456 }];
        expectedResult = service.addPolicyUsersToCollectionIfMissing(policyUsersCollection, policyUsers);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(policyUsers);
      });

      it('should add only unique PolicyUsers to an array', () => {
        const policyUsersArray: IPolicyUsers[] = [{ id: 123 }, { id: 456 }, { id: 4163 }];
        const policyUsersCollection: IPolicyUsers[] = [{ id: 123 }];
        expectedResult = service.addPolicyUsersToCollectionIfMissing(policyUsersCollection, ...policyUsersArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const policyUsers: IPolicyUsers = { id: 123 };
        const policyUsers2: IPolicyUsers = { id: 456 };
        expectedResult = service.addPolicyUsersToCollectionIfMissing([], policyUsers, policyUsers2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(policyUsers);
        expect(expectedResult).toContain(policyUsers2);
      });

      it('should accept null and undefined values', () => {
        const policyUsers: IPolicyUsers = { id: 123 };
        expectedResult = service.addPolicyUsersToCollectionIfMissing([], null, policyUsers, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(policyUsers);
      });

      it('should return initial array if no PolicyUsers is added', () => {
        const policyUsersCollection: IPolicyUsers[] = [{ id: 123 }];
        expectedResult = service.addPolicyUsersToCollectionIfMissing(policyUsersCollection, undefined, null);
        expect(expectedResult).toEqual(policyUsersCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
