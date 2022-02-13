import { IAgency } from 'app/entities/agency/agency.model';
import { ICompany } from 'app/entities/company/company.model';
import { IProduct } from 'app/entities/product/product.model';
import { IPremiunDetails } from 'app/entities/premiun-details/premiun-details.model';
import { IVehicleClass } from 'app/entities/vehicle-class/vehicle-class.model';
import { IBankDetails } from 'app/entities/bank-details/bank-details.model';
import { INominee } from 'app/entities/nominee/nominee.model';
import { IMember } from 'app/entities/member/member.model';
import { IPolicyUsers } from 'app/entities/policy-users/policy-users.model';
import { PremiumMode } from 'app/entities/enumerations/premium-mode.model';
import { PolicyStatus } from 'app/entities/enumerations/policy-status.model';
import { Zone } from 'app/entities/enumerations/zone.model';
import { PolicyType } from 'app/entities/enumerations/policy-type.model';

export interface IPolicy {
  id?: number;
  policyAmount?: number | null;
  policyNumber?: string | null;
  term?: number | null;
  ppt?: number | null;
  commDate?: string;
  proposerName?: string | null;
  sumAssuredAmount?: number | null;
  premiumMode?: PremiumMode | null;
  basicPremium?: number | null;
  extraPremium?: number | null;
  gst?: string | null;
  status?: PolicyStatus | null;
  totalPremiun?: string | null;
  gstFirstYear?: string | null;
  netPremium?: string | null;
  taxBeneficiary?: string | null;
  policyReceived?: boolean | null;
  previousPolicy?: number | null;
  policyStartDate?: string | null;
  policyEndDate?: string | null;
  period?: string | null;
  claimDone?: boolean | null;
  freeHeathCheckup?: boolean | null;
  zone?: Zone | null;
  noOfYear?: number | null;
  floaterSum?: string | null;
  tpa?: string | null;
  paymentDate?: string | null;
  policyType?: PolicyType | null;
  paToOwner?: string | null;
  paToOther?: string | null;
  loading?: number | null;
  riskCoveredFrom?: string | null;
  riskCoveredTo?: string | null;
  notes?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freeField4?: string | null;
  freeField5?: string | null;
  maturityDate?: string;
  uinNo?: string | null;
  lastModified?: string;
  lastModifiedBy?: string;
  agency?: IAgency | null;
  company?: ICompany | null;
  product?: IProduct | null;
  premiunDetails?: IPremiunDetails | null;
  vehicleClass?: IVehicleClass | null;
  bankDetails?: IBankDetails | null;
  nominees?: INominee[] | null;
  members?: IMember[] | null;
  policyUsers?: IPolicyUsers | null;
}

export class Policy implements IPolicy {
  constructor(
    public id?: number,
    public policyAmount?: number | null,
    public policyNumber?: string | null,
    public term?: number | null,
    public ppt?: number | null,
    public commDate?: string,
    public proposerName?: string | null,
    public sumAssuredAmount?: number | null,
    public premiumMode?: PremiumMode | null,
    public basicPremium?: number | null,
    public extraPremium?: number | null,
    public gst?: string | null,
    public status?: PolicyStatus | null,
    public totalPremiun?: string | null,
    public gstFirstYear?: string | null,
    public netPremium?: string | null,
    public taxBeneficiary?: string | null,
    public policyReceived?: boolean | null,
    public previousPolicy?: number | null,
    public policyStartDate?: string | null,
    public policyEndDate?: string | null,
    public period?: string | null,
    public claimDone?: boolean | null,
    public freeHeathCheckup?: boolean | null,
    public zone?: Zone | null,
    public noOfYear?: number | null,
    public floaterSum?: string | null,
    public tpa?: string | null,
    public paymentDate?: string | null,
    public policyType?: PolicyType | null,
    public paToOwner?: string | null,
    public paToOther?: string | null,
    public loading?: number | null,
    public riskCoveredFrom?: string | null,
    public riskCoveredTo?: string | null,
    public notes?: string | null,
    public freeField1?: string | null,
    public freeField2?: string | null,
    public freeField3?: string | null,
    public freeField4?: string | null,
    public freeField5?: string | null,
    public maturityDate?: string,
    public uinNo?: string | null,
    public lastModified?: string,
    public lastModifiedBy?: string,
    public agency?: IAgency | null,
    public company?: ICompany | null,
    public product?: IProduct | null,
    public premiunDetails?: IPremiunDetails | null,
    public vehicleClass?: IVehicleClass | null,
    public bankDetails?: IBankDetails | null,
    public nominees?: INominee[] | null,
    public members?: IMember[] | null,
    public policyUsers?: IPolicyUsers | null
  ) {
    this.policyReceived = this.policyReceived ?? false;
    this.claimDone = this.claimDone ?? false;
    this.freeHeathCheckup = this.freeHeathCheckup ?? false;
  }
}

export function getPolicyIdentifier(policy: IPolicy): number | undefined {
  return policy.id;
}
