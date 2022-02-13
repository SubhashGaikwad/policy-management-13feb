import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { Zone } from 'app/entities/enumerations/zone.model';
import { IVehicleDetails, VehicleDetails } from '../vehicle-details.model';

import { VehicleDetailsService } from './vehicle-details.service';

describe('VehicleDetails Service', () => {
  let service: VehicleDetailsService;
  let httpMock: HttpTestingController;
  let elemDefault: IVehicleDetails;
  let expectedResult: IVehicleDetails | IVehicleDetails[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(VehicleDetailsService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      name: 0,
      invoiceValue: 'AAAAAAA',
      idv: 'AAAAAAA',
      enginNumber: 'AAAAAAA',
      chassisNumber: 'AAAAAAA',
      registrationNumber: 'AAAAAAA',
      seatingCapacity: 0,
      zone: Zone.A,
      yearOfManufacturing: 'AAAAAAA',
      registrationDate: 'AAAAAAA',
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

    it('should create a VehicleDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new VehicleDetails()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a VehicleDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 1,
          invoiceValue: 'BBBBBB',
          idv: 'BBBBBB',
          enginNumber: 'BBBBBB',
          chassisNumber: 'BBBBBB',
          registrationNumber: 'BBBBBB',
          seatingCapacity: 1,
          zone: 'BBBBBB',
          yearOfManufacturing: 'BBBBBB',
          registrationDate: 'BBBBBB',
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

    it('should partial update a VehicleDetails', () => {
      const patchObject = Object.assign(
        {
          name: 1,
          chassisNumber: 'BBBBBB',
          zone: 'BBBBBB',
          registrationDate: 'BBBBBB',
          lastModified: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
        },
        new VehicleDetails()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of VehicleDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 1,
          invoiceValue: 'BBBBBB',
          idv: 'BBBBBB',
          enginNumber: 'BBBBBB',
          chassisNumber: 'BBBBBB',
          registrationNumber: 'BBBBBB',
          seatingCapacity: 1,
          zone: 'BBBBBB',
          yearOfManufacturing: 'BBBBBB',
          registrationDate: 'BBBBBB',
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

    it('should delete a VehicleDetails', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addVehicleDetailsToCollectionIfMissing', () => {
      it('should add a VehicleDetails to an empty array', () => {
        const vehicleDetails: IVehicleDetails = { id: 123 };
        expectedResult = service.addVehicleDetailsToCollectionIfMissing([], vehicleDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(vehicleDetails);
      });

      it('should not add a VehicleDetails to an array that contains it', () => {
        const vehicleDetails: IVehicleDetails = { id: 123 };
        const vehicleDetailsCollection: IVehicleDetails[] = [
          {
            ...vehicleDetails,
          },
          { id: 456 },
        ];
        expectedResult = service.addVehicleDetailsToCollectionIfMissing(vehicleDetailsCollection, vehicleDetails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a VehicleDetails to an array that doesn't contain it", () => {
        const vehicleDetails: IVehicleDetails = { id: 123 };
        const vehicleDetailsCollection: IVehicleDetails[] = [{ id: 456 }];
        expectedResult = service.addVehicleDetailsToCollectionIfMissing(vehicleDetailsCollection, vehicleDetails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(vehicleDetails);
      });

      it('should add only unique VehicleDetails to an array', () => {
        const vehicleDetailsArray: IVehicleDetails[] = [{ id: 123 }, { id: 456 }, { id: 12382 }];
        const vehicleDetailsCollection: IVehicleDetails[] = [{ id: 123 }];
        expectedResult = service.addVehicleDetailsToCollectionIfMissing(vehicleDetailsCollection, ...vehicleDetailsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const vehicleDetails: IVehicleDetails = { id: 123 };
        const vehicleDetails2: IVehicleDetails = { id: 456 };
        expectedResult = service.addVehicleDetailsToCollectionIfMissing([], vehicleDetails, vehicleDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(vehicleDetails);
        expect(expectedResult).toContain(vehicleDetails2);
      });

      it('should accept null and undefined values', () => {
        const vehicleDetails: IVehicleDetails = { id: 123 };
        expectedResult = service.addVehicleDetailsToCollectionIfMissing([], null, vehicleDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(vehicleDetails);
      });

      it('should return initial array if no VehicleDetails is added', () => {
        const vehicleDetailsCollection: IVehicleDetails[] = [{ id: 123 }];
        expectedResult = service.addVehicleDetailsToCollectionIfMissing(vehicleDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(vehicleDetailsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
