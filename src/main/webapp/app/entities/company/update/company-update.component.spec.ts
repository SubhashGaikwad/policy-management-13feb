import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CompanyService } from '../service/company.service';
import { ICompany, Company } from '../company.model';
import { ICompanyType } from 'app/entities/company-type/company-type.model';
import { CompanyTypeService } from 'app/entities/company-type/service/company-type.service';

import { CompanyUpdateComponent } from './company-update.component';

describe('Company Management Update Component', () => {
  let comp: CompanyUpdateComponent;
  let fixture: ComponentFixture<CompanyUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let companyService: CompanyService;
  let companyTypeService: CompanyTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CompanyUpdateComponent],
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
      .overrideTemplate(CompanyUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CompanyUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    companyService = TestBed.inject(CompanyService);
    companyTypeService = TestBed.inject(CompanyTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call companyType query and add missing value', () => {
      const company: ICompany = { id: 456 };
      const companyType: ICompanyType = { id: 7389 };
      company.companyType = companyType;

      const companyTypeCollection: ICompanyType[] = [{ id: 33907 }];
      jest.spyOn(companyTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: companyTypeCollection })));
      const expectedCollection: ICompanyType[] = [companyType, ...companyTypeCollection];
      jest.spyOn(companyTypeService, 'addCompanyTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ company });
      comp.ngOnInit();

      expect(companyTypeService.query).toHaveBeenCalled();
      expect(companyTypeService.addCompanyTypeToCollectionIfMissing).toHaveBeenCalledWith(companyTypeCollection, companyType);
      expect(comp.companyTypesCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const company: ICompany = { id: 456 };
      const companyType: ICompanyType = { id: 7687 };
      company.companyType = companyType;

      activatedRoute.data = of({ company });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(company));
      expect(comp.companyTypesCollection).toContain(companyType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Company>>();
      const company = { id: 123 };
      jest.spyOn(companyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ company });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: company }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(companyService.update).toHaveBeenCalledWith(company);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Company>>();
      const company = new Company();
      jest.spyOn(companyService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ company });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: company }));
      saveSubject.complete();

      // THEN
      expect(companyService.create).toHaveBeenCalledWith(company);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Company>>();
      const company = { id: 123 };
      jest.spyOn(companyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ company });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(companyService.update).toHaveBeenCalledWith(company);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackCompanyTypeById', () => {
      it('Should return tracked CompanyType primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCompanyTypeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
