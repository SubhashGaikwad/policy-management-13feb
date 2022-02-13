import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PolicyUsersDetailComponent } from './policy-users-detail.component';

describe('PolicyUsers Management Detail Component', () => {
  let comp: PolicyUsersDetailComponent;
  let fixture: ComponentFixture<PolicyUsersDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PolicyUsersDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ policyUsers: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PolicyUsersDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PolicyUsersDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load policyUsers on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.policyUsers).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
