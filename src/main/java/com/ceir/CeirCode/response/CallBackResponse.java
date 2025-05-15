package com.ceir.CeirCode.response;

public class CallBackResponse {
		private Integer status;
		private String tran_id;
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		public String getTran_id() {
			return tran_id;
		}
		public void setTran_id(String tran_id) {
			this.tran_id = tran_id;
		}
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("PaymentResponse [status=");
			builder.append(status);
			builder.append(", tran_id=");
			builder.append(tran_id);
			builder.append("]");
			return builder.toString();
		}
	}

