/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

#ifndef USB_HH
#define USB_HH

/**
 * This file contains enumerations and classes related to the USB devices classes.
 */

class USBInfo {

	enum USBClasses {
		Unspecified							= 0x00,	/*   0 */
		Audio								= 0x01,	/*   1 */
		Communication_CDCControl			= 0x02	/*   2 */
		HID-HumanInterfaceDevice			= 0x03,	/*   3 */
		PID-PhysicalInterfaceDevice			= 0x05,	/*   5 */
		Image								= 0x06,	/*   6 */
		Printer								= 0x07,	/*   7 */
		MassStorage							= 0x08,	/*   8 */
		USBHub								= 0x09,	/*   9 */
		CDC-Data							= 0x0A,	/*  10 */
		SmartCard							= 0x0B,	/*  11 */
		ContentSecurity						= 0x0D,	/*  13 */
		Video								= 0x0E,	/*  14 */
		PersonalHealthCare					= 0x0F,	/*  15 */
		AV-AudioVideo						= 0x10,	/*  16 */
		Billboard							= 0x11,	/*  17 */
		DiagnosticDevice					= 0xDC,	/* 220 */
		WirelessController					= 0xE0,	/* 224 */
		Miscellaneous,						= 0xEF,	/* 239 */
		ApplicationSpecific-IrDA_TMC_DFU	= 0xFE,	/* 254 */
		VendorSpecific						= 0xFF 	/* 255 */
	}

	/* To use the following, declare:
	 * 		constexpr const char * const USBInfo::USBClassNames[];
	 * where you need to use it
	 */
	static const char * const USBClassNamesArray[] {
		"Unspecified",						/*   0 */
		"Audio",							/*   1 */
		"Communication_CDCControl",			/*   2 */
		"HID-HumanInterfaceDevice",			/*   3 */
		"?",								/*   4 */
		"PID-PhysicalInterfaceDevice",		/*   5 */
		"Image",							/*   6 */
		"Printer",							/*   7 */
		"MassStorage",						/*   8 */
		"USBHub",							/*   9 */
		"CDC-Data",							/*  10 */
		"SmartCard",						/*  11 */
		"?",								/*  12 */
		"ContentSecurity",					/*  13 */
		"Video",							/*  14 */
		"PersonalHealthCard",				/*  15 */
		"AV-AudioVideo",					/*  16 */
		"Billboard",						/*  17 */
		"?", "?", "?", "?", "?",			/* 18-22 */
		"?", "?", "?", "?", "?",			/* 23-27 */
		"?", "?", "?", "?", "?",			/* 28-32 */
		"?", "?", "?", "?", "?",			/* 33-37 */
		"?", "?", "?", "?", "?",			/* 38-42 */
		"?", "?", "?", "?", "?",			/* 43-47 */
		"?", "?", "?", "?", "?",			/* 48-52 */
		"?", "?", "?", "?", "?",			/* 53-57 */
		"?", "?", "?", "?", "?",		 	/* 58-62 */
		"?", "?", "?", "?", "?",		 	/* 53-67 */
		"?", "?", "?", "?", "?",	 		/* 68-72 */
		"?", "?", "?", "?", "?",		 	/* 63-77 */
		"?", "?", "?", "?", "?",		 	/* 78-82 */
		"?", "?", "?", "?", "?",	 		/* 73-87 */
		"?", "?", "?", "?", "?",		 	/* 88-92 */
		"?", "?", "?", "?", "?",		 	/* 83-97 */
		"?", "?", "?", "?", "?",	 		/* 98-102 */
		"?", "?", "?", "?", "?",		 	/* 103-107 */
		"?", "?", "?", "?", "?",		 	/* 108-112 */
		"?", "?", "?", "?", "?",	 		/* 113-117 */
		"?", "?", "?", "?", "?",		 	/* 118-122 */
		"?", "?", "?", "?", "?",		 	/* 123-127 */
		"?", "?", "?", "?", "?",	 		/* 128-132 */
		"?", "?", "?", "?", "?",		 	/* 133-137 */
		"?", "?", "?", "?", "?",		 	/* 138-142 */
		"?", "?", "?", "?", "?",	 		/* 143-147 */
		"?", "?", "?", "?", "?",		 	/* 148-152 */
		"?", "?", "?", "?", "?",		 	/* 153-157 */
		"?", "?", "?", "?", "?",	 		/* 158-162 */
		"?", "?", "?", "?", "?",		 	/* 163-167 */
		"?", "?", "?", "?", "?",		 	/* 168-172 */
		"?", "?", "?", "?", "?",	 		/* 173-177 */
		"?", "?", "?", "?", "?",		 	/* 178-182 */
		"?", "?", "?", "?", "?",		 	/* 183-187 */
		"?", "?", "?", "?", "?",	 		/* 188-192 */
		"?", "?", "?", "?", "?",		 	/* 193-197 */
		"?", "?", "?", "?", "?",		 	/* 198-202 */
		"?", "?", "?", "?", "?",	 		/* 203-207 */
		"?", "?", "?", "?", "?",		 	/* 208-212 */
		"?", "?", "?", "?", "?",		 	/* 213-217 */
		"?", "?",							/* 218-219 */
		"DiagnosticDevice",					/* 220 */
		"?", "?", "?",						/* 221-223 */
		"WirelessController",				/* 224 */
		"?", "?", "?", "?", "?",	 		/* 225-229 */
		"?", "?", "?", "?", "?",		 	/* 230-234 */
		"?", "?", "?", "?", 				/* 235-238 */
		"Miscellaneous",					/* 239 */
		"?", "?", "?", "?", "?",		 	/* 240-244 */
		"?", "?", "?", "?", "?",	 		/* 245-249 */
		"?", "?", "?", "?", 				/* 250-253 */
		"ApplicationSpecific-IrDA_TMC_DFU",	/* 254 */
		"VendorSpecific"					/* 255 */
	}
}

#endif
