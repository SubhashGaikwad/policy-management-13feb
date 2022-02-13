import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ParameterLookupService } from '../service/parameter-lookup.service';
import { IParameterLookup, ParameterLookup } from '../parameter-lookup.model';
import { IVehicleDetails } from 'app/entities/vehicle-details/vehicle-details.model';
import { VehicleDetailsService } from 'app/entities/vehicle-details/service/vehicle-details.service';

import { ParameterLookupUpdateComponent } from './parameter-lookup-update.component';

describe('ParameterLookup Management Update Component', () => {
  let comp: ParameterLookupUpdateComponent;
  let fixture: ComponentFixture<ParameterLookupUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let parameterLookupService: ParameterLookupService;
  let vehicleDetailsService: VehicleDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ParameterLookupUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ParameterLookupUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ParameterLookupUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    parameterLookupService = TestBed.inject(ParameterLookupService);
    vehicleDetailsService = TestBed.inject(VehicleDetailsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call VehicleDetails query and add missing value', () => {
      const parameterLookup: IParameterLookup = { id: 456 };
      const vehicleDetails: IVehicleDetails = { id: 92150 };
      parameterLookup.vehicleDetails = vehicleDetails;

      const vehicleDetailsCollection: IVehicleDetails[] = [{ id: 43368 }];
      jest.spyOn(vehicleDetailsService, 'query').mockReturnValue(of(new HttpResponse({ body: vehicleDetailsCollection })));
      const additionalVehicleDetails = [vehicleDetails];
      const expectedCollection: IVehicleDetails[] = [...additionalVehicleDetails, ...vehicleDetailsCollection];
      jest.spyOn(vehicleDetailsService, 'addVehicleDetailsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ parameterLookup });
      comp.ngOnInit();

      expect(vehicleDetailsService.query).toHaveBeenCalled();
      expect(vehicleDetailsService.addVehicleDetailsToCollectionIfMissing).toHaveBeenCalledWith(
        vehicleDetailsCollection,
        ...additionalVehicleDetails
      );
      expect(comp.vehicleDetailsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const parameterLookup: IParameterLookup = { id: 456 };
      const vehicleDetails: IVehicleDetails = { id: 86002 };
      parameterLookup.vehicleDetails = vehicleDetails;

      activatedRoute.data = of({ parameterLookup });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(parameterLookup));
      expect(comp.vehicleDetailsSharedCollection).toContain(vehicleDetails);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ParameterLookup>>();
      const parameterLookup = { id: 123 };
      jest.spyOn(parameterLookupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ parameterLookup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: parameterLookup }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(parameterLookupService.update).toHaveBeenCalledWith(parameterLookup);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ParameterLookup>>();
      const parameterLookup = new ParameterLookup();
      jest.spyOn(parameterLookupService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ parameterLookup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: parameterLookup }));
      saveSubject.complete();

      // THEN
      expect(parameterLookupService.create).toHaveBeenCalledWith(parameterLookup);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ParameterLookup>>();
      const parameterLookup = { id: 123 };
      jest.spyOn(parameterLookupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ parameterLookup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(parameterLookupService.update).toHaveBeenCalledWith(parameterLookup);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackVehicleDetailsById', () => {
      it('Should return tracked VehicleDetails primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackVehicleDetailsById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
