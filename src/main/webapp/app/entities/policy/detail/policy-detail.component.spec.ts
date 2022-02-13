import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PolicyDetailComponent } from './policy-detail.component';

describe('Policy Management Detail Component', () => {
  let comp: PolicyDetailComponent;
  let fixture: ComponentFixture<PolicyDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PolicyDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ policy: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PolicyDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PolicyDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load policy on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.policy).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
