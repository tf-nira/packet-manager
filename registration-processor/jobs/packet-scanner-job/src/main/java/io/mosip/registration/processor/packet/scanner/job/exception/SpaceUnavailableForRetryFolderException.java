package io.mosip.registration.processor.packet.scanner.job.exception;

import io.mosip.kernel.core.exception.BaseUncheckedException;
import io.mosip.registration.processor.core.exception.util.PlatformErrorCodes;
/**
 * The Class SpaceUnavailableForRetryFolderException.
 */
public class SpaceUnavailableForRetryFolderException extends BaseUncheckedException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new space unavailable for retry folder exception.
	 *
	 * @param errorMessage the error message
	 */
	public SpaceUnavailableForRetryFolderException(String errorMessage) {
		super(PlatformErrorCodes.RPR_PSJ_SPACE_UNAVAILABLE_FOR_RETRY_FOLDER, errorMessage);
	}

	/**
	 * Instantiates a new space unavailable for retry folder exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public SpaceUnavailableForRetryFolderException(String message, Throwable cause) {
		super(PlatformErrorCodes.RPR_PSJ_SPACE_UNAVAILABLE_FOR_RETRY_FOLDER + EMPTY_SPACE, message, cause);
	}

}
