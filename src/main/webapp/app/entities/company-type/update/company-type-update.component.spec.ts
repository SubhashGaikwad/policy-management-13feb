import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CompanyTypeService } from '../service/company-type.service';
import { ICompanyType, CompanyType } from '../company-type.model';

import { CompanyTypeUpdateComponent } from './company-type-update.component';

describe('CompanyType Management Update Component', () => {
  let comp: CompanyTypeUpdateComponent;
  let fixture: ComponentFixture<CompanyTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let companyTypeService: CompanyTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CompanyTypeUpdateComponent],
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
      .overrideTemplate(CompanyTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CompanyTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    companyTypeService = TestBed.inject(CompanyTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const companyType: ICompanyType = { id: 456 };

      activatedRoute.data = of({ companyType });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(companyType));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CompanyType>>();
      const companyType = { id: 123 };
      jest.spyOn(companyTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ companyType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: companyType }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(companyTypeService.update).toHaveBeenCalledWith(companyType);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CompanyType>>();
      const companyType = new CompanyType();
      jest.spyOn(companyTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ companyType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: companyType }));
      saveSubject.complete();

      // THEN
      expect(companyTypeService.create).toHaveBeenCalledWith(companyType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CompanyType>>();
      const companyType = { id: 123 };
      jest.spyOn(companyTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ companyType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(companyTypeService.update).toHaveBeenCalledWith(companyType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
