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
 * This enumeration lists the defined classes of USB devices and is conform to the USB specification.
 * Missing elements are also undefined or reserved in the specification.
 */

enum USBDeviceClasses {
	Unspecified							= 0x00,	/*   0 */
	Audio								= 0x01,	/*   1 */
	Communication_CDCControl			= 0x02,	/*   2 */
	HID_HumanInterfaceDevice			= 0x03,	/*   3 */
	PID_PhysicalInterfaceDevice			= 0x05,	/*   5 */
	Image								= 0x06,	/*   6 */
	Printer								= 0x07,	/*   7 */
	MassStorage							= 0x08,	/*   8 */
	USBHub								= 0x09,	/*   9 */
	CDC_Data							= 0x0A,	/*  10 */
	SmartCard							= 0x0B,	/*  11 */
	ContentSecurity						= 0x0D,	/*  13 */
	Video								= 0x0E,	/*  14 */
	PersonalHealthCare					= 0x0F,	/*  15 */
	AV_AudioVideo						= 0x10,	/*  16 */
	Billboard							= 0x11,	/*  17 */
	DiagnosticDevice					= 0xDC,	/* 220 */
	WirelessController					= 0xE0,	/* 224 */
	Miscellaneous 						= 0xEF,	/* 239 */
	ApplicationSpecific_IrDA_TMC_DFU	= 0xFE,	/* 254 */
	VendorSpecific						= 0xFF 	/* 255 */
};

#endif
