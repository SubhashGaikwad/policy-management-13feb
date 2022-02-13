import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { VehicleClassService } from '../service/vehicle-class.service';
import { IVehicleClass, VehicleClass } from '../vehicle-class.model';

import { VehicleClassUpdateComponent } from './vehicle-class-update.component';

describe('VehicleClass Management Update Component', () => {
  let comp: VehicleClassUpdateComponent;
  let fixture: ComponentFixture<VehicleClassUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let vehicleClassService: VehicleClassService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [VehicleClassUpdateComponent],
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
      .overrideTemplate(VehicleClassUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VehicleClassUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    vehicleClassService = TestBed.inject(VehicleClassService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const vehicleClass: IVehicleClass = { id: 456 };

      activatedRoute.data = of({ vehicleClass });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(vehicleClass));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<VehicleClass>>();
      const vehicleClass = { id: 123 };
      jest.spyOn(vehicleClassService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vehicleClass });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: vehicleClass }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(vehicleClassService.update).toHaveBeenCalledWith(vehicleClass);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<VehicleClass>>();
      const vehicleClass = new VehicleClass();
      jest.spyOn(vehicleClassService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vehicleClass });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: vehicleClass }));
      saveSubject.complete();

      // THEN
      expect(vehicleClassService.create).toHaveBeenCalledWith(vehicleClass);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<VehicleClass>>();
      const vehicleClass = { id: 123 };
      jest.spyOn(vehicleClassService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vehicleClass });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(vehicleClassService.update).toHaveBeenCalledWith(vehicleClass);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
