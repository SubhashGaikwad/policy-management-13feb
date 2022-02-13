import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IBankDetails, BankDetails } from '../bank-details.model';

import { BankDetailsService } from './bank-details.service';

describe('BankDetails Service', () => {
  let service: BankDetailsService;
  let httpMock: HttpTestingController;
  let elemDefault: IBankDetails;
  let expectedResult: IBankDetails | IBankDetails[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BankDetailsService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
      branch: 'AAAAAAA',
      branchCode: 'AAAAAAA',
      city: 0,
      contactNo: 0,
      ifcCode: 'AAAAAAA',
      account: 'AAAAAAA',
      accountType: 'AAAAAAA',
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

    it('should create a BankDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new BankDetails()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a BankDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          branch: 'BBBBBB',
          branchCode: 'BBBBBB',
          city: 1,
          contactNo: 1,
          ifcCode: 'BBBBBB',
          account: 'BBBBBB',
          accountType: 'BBBBBB',
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

    it('should partial update a BankDetails', () => {
      const patchObject = Object.assign(
        {
          branch: 'BBBBBB',
          branchCode: 'BBBBBB',
          city: 1,
          contactNo: 1,
          account: 'BBBBBB',
          accountType: 'BBBBBB',
        },
        new BankDetails()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of BankDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          branch: 'BBBBBB',
          branchCode: 'BBBBBB',
          city: 1,
          contactNo: 1,
          ifcCode: 'BBBBBB',
          account: 'BBBBBB',
          accountType: 'BBBBBB',
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

    it('should delete a BankDetails', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addBankDetailsToCollectionIfMissing', () => {
      it('should add a BankDetails to an empty array', () => {
        const bankDetails: IBankDetails = { id: 123 };
        expectedResult = service.addBankDetailsToCollectionIfMissing([], bankDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bankDetails);
      });

      it('should not add a BankDetails to an array that contains it', () => {
        const bankDetails: IBankDetails = { id: 123 };
        const bankDetailsCollection: IBankDetails[] = [
          {
            ...bankDetails,
          },
          { id: 456 },
        ];
        expectedResult = service.addBankDetailsToCollectionIfMissing(bankDetailsCollection, bankDetails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a BankDetails to an array that doesn't contain it", () => {
        const bankDetails: IBankDetails = { id: 123 };
        const bankDetailsCollection: IBankDetails[] = [{ id: 456 }];
        expectedResult = service.addBankDetailsToCollectionIfMissing(bankDetailsCollection, bankDetails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bankDetails);
      });

      it('should add only unique BankDetails to an array', () => {
        const bankDetailsArray: IBankDetails[] = [{ id: 123 }, { id: 456 }, { id: 49916 }];
        const bankDetailsCollection: IBankDetails[] = [{ id: 123 }];
        expectedResult = service.addBankDetailsToCollectionIfMissing(bankDetailsCollection, ...bankDetailsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const bankDetails: IBankDetails = { id: 123 };
        const bankDetails2: IBankDetails = { id: 456 };
        expectedResult = service.addBankDetailsToCollectionIfMissing([], bankDetails, bankDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bankDetails);
        expect(expectedResult).toContain(bankDetails2);
      });

      it('should accept null and undefined values', () => {
        const bankDetails: IBankDetails = { id: 123 };
        expectedResult = service.addBankDetailsToCollectionIfMissing([], null, bankDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bankDetails);
      });

      it('should return initial array if no BankDetails is added', () => {
        const bankDetailsCollection: IBankDetails[] = [{ id: 123 }];
        expectedResult = service.addBankDetailsToCollectionIfMissing(bankDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(bankDetailsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
