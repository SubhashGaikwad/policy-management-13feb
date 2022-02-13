import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IVehicleClass, VehicleClass } from '../vehicle-class.model';

import { VehicleClassService } from './vehicle-class.service';

describe('VehicleClass Service', () => {
  let service: VehicleClassService;
  let httpMock: HttpTestingController;
  let elemDefault: IVehicleClass;
  let expectedResult: IVehicleClass | IVehicleClass[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(VehicleClassService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      name: 0,
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

    it('should create a VehicleClass', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new VehicleClass()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a VehicleClass', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 1,
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

    it('should partial update a VehicleClass', () => {
      const patchObject = Object.assign(
        {
          lastModified: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
        },
        new VehicleClass()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of VehicleClass', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 1,
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

    it('should delete a VehicleClass', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addVehicleClassToCollectionIfMissing', () => {
      it('should add a VehicleClass to an empty array', () => {
        const vehicleClass: IVehicleClass = { id: 123 };
        expectedResult = service.addVehicleClassToCollectionIfMissing([], vehicleClass);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(vehicleClass);
      });

      it('should not add a VehicleClass to an array that contains it', () => {
        const vehicleClass: IVehicleClass = { id: 123 };
        const vehicleClassCollection: IVehicleClass[] = [
          {
            ...vehicleClass,
          },
          { id: 456 },
        ];
        expectedResult = service.addVehicleClassToCollectionIfMissing(vehicleClassCollection, vehicleClass);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a VehicleClass to an array that doesn't contain it", () => {
        const vehicleClass: IVehicleClass = { id: 123 };
        const vehicleClassCollection: IVehicleClass[] = [{ id: 456 }];
        expectedResult = service.addVehicleClassToCollectionIfMissing(vehicleClassCollection, vehicleClass);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(vehicleClass);
      });

      it('should add only unique VehicleClass to an array', () => {
        const vehicleClassArray: IVehicleClass[] = [{ id: 123 }, { id: 456 }, { id: 18759 }];
        const vehicleClassCollection: IVehicleClass[] = [{ id: 123 }];
        expectedResult = service.addVehicleClassToCollectionIfMissing(vehicleClassCollection, ...vehicleClassArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const vehicleClass: IVehicleClass = { id: 123 };
        const vehicleClass2: IVehicleClass = { id: 456 };
        expectedResult = service.addVehicleClassToCollectionIfMissing([], vehicleClass, vehicleClass2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(vehicleClass);
        expect(expectedResult).toContain(vehicleClass2);
      });

      it('should accept null and undefined values', () => {
        const vehicleClass: IVehicleClass = { id: 123 };
        expectedResult = service.addVehicleClassToCollectionIfMissing([], null, vehicleClass, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(vehicleClass);
      });

      it('should return initial array if no VehicleClass is added', () => {
        const vehicleClassCollection: IVehicleClass[] = [{ id: 123 }];
        expectedResult = service.addVehicleClassToCollectionIfMissing(vehicleClassCollection, undefined, null);
        expect(expectedResult).toEqual(vehicleClassCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
