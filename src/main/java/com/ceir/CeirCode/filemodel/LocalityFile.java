package com.ceir.CeirCode.filemodel;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class LocalityFile {
	  
		@CsvBindByName(column = "Created On")
		@CsvBindByPosition(position = 0)
		private String createdOn;
		
		 
		@CsvBindByName(column = "Modified On")
		@CsvBindByPosition(position = 1)
		private String modifiedOn;
		
		/*
		 * @CsvBindByName(column = "Country")
		 * 
		 * @CsvBindByPosition(position = 2) private String country;
		 */
		
		@CsvBindByName(column = "Province")
		@CsvBindByPosition(position = 2)
		private String province;
		
		
		@CsvBindByName(column = "District")
		@CsvBindByPosition(position = 3)
		private String district;
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("LocalityFile [createdOn=");
			builder.append(createdOn);
			builder.append(", modifiedOn=");
			builder.append(modifiedOn);
			builder.append(", province=");
			builder.append(province);
			builder.append(", district=");
			builder.append(district);
			builder.append(", commune=");
			builder.append(commune);
			builder.append(", village=");
			builder.append(village);
			builder.append("]");
			return builder.toString();
		}

		public String getCreatedOn() {
			return createdOn;
		}

		public void setCreatedOn(String createdOn) {
			this.createdOn = createdOn;
		}

		public String getModifiedOn() {
			return modifiedOn;
		}

		public void setModifiedOn(String modifiedOn) {
			this.modifiedOn = modifiedOn;
		}

		public String getProvince() {
			return province;
		}

		public void setProvince(String province) {
			this.province = province;
		}

		public String getDistrict() {
			return district;
		}

		public void setDistrict(String district) {
			this.district = district;
		}

		public String getCommune() {
			return commune;
		}

		public void setCommune(String commune) {
			this.commune = commune;
		}

		public String getVillage() {
			return village;
		}

		public void setVillage(String village) {
			this.village = village;
		}

		@CsvBindByName(column = "Commune")
		@CsvBindByPosition(position = 4)
		private String commune;
		
		@CsvBindByName(column = "Village")
		@CsvBindByPosition(position = 5)
		private String village;
		
}
