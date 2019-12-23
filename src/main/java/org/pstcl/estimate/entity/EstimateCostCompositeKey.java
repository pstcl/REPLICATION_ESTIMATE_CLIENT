package org.pstcl.estimate.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Embeddable
public class EstimateCostCompositeKey  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
    @Cascade(value={org.hibernate.annotations.CascadeType.ALL})
	@JoinColumn(name="estimate_code", referencedColumnName="estimate_code")
	private Estimate estimate;

	private Integer mid;

	@JsonIgnore
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EstimateCostCompositeKey other = (EstimateCostCompositeKey) obj;
		if (estimate == null) {
			if (other.estimate != null)
				return false;
		} 
		else if(!estimate.getEstimateCode().equals(other.estimate.getEstimateCode())) 
		{
			return false;
		}
		if (mid == null) {
			if (other.mid != null)
				return false;
		} else if (!mid.equals(other.mid))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + ((estimate== null) ? 0 : ((estimate.getEstimateCode()==null)?0:estimate.getEstimateCode().hashCode()));
		result = prime * result + ((mid == null) ? 0 : mid.hashCode());
		return result;
	}

	@JsonIgnore
	@Override
	public String toString() {
		return "EstimateCostCompositeKey [estimate=" + estimate.getEstimateCode() + ", mid=" + mid + "]";
	}

	
	
	

}