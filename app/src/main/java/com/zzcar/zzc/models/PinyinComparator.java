package com.zzcar.zzc.models;

import com.zzcar.zzc.networks.responses.BrandListResponse;

import java.util.Comparator;

/**
 * 
 * @author xiaanming
 *
 */
public class PinyinComparator implements Comparator<BrandListResponse> {

	public int compare(BrandListResponse o1, BrandListResponse o2) {
		if (o1.getFirst_letter().equals("@")
				|| o2.getFirst_letter().equals("#")) {
			return -1;
		} else if (o1.getFirst_letter().equals("#")
				|| o2.getFirst_letter().equals("@")) {
			return 1;
		} else {
			return o1.getFirst_letter().compareTo(o2.getFirst_letter());
		}
	}

}
