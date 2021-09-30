/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

#include usb.hh
#include <map>
#include <string>

class USBInfo {

public:

	static const std::map<int,string> = USBClassNamesMap {
		{   0, "Unspecified"},
		{   1, "Audio" },
		{   2, "Communication_CDCControl" }, 
		{   3, "HID-HumanInterfaceDevice" },
		{   5, "PID-PhysicalInterfaceDevice" },
		{   6, "Image" },
		{   7, "Printer" },
		{   8, "MassStorage" },
		{   9, "USBHub" },
		{  10, "CDC-Data" },
		{  11, "SmartCard" },
		{  12, "?" },
		{  13, "ContentSecurity" },
		{  14, "Video" },
		{  15, "PersonalHealthCard" },
		{  16, "AV-AudioVideo" },
		{  17, "Billboard" },
		{ 220, "DiagnosticDevice" },
		{ 224, "WirelessController" },
		{ 239, "Miscellaneous" },
		{ 254, "ApplicationSpecific-IrDA_TMC_DFU" },
		{ 255, "VendorSpecific" }
	}
}
