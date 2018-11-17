package hello;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
public class LogMessage {

	@NotBlank
	private String id;
	@NotBlank
	private String state;
	private String type;
	private String host;
	@NotNull
	private String timestamp;
	private Integer lineNumber;

}
