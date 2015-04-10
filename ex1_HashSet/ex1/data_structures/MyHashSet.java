package clids.ex1.data_structures;

import java.util.LinkedList;

public class MyHashSet {
	private static final int DEFAULT_CAPACITY = 16;
	private static final double DEFAULT_LOWER_LOAD_FACTOR = 0.25;
	private static final double DEFAULT_UPPER_LOAD_FACTOR = 0.75;
	private int capacity, size;
	private double upperLoadFactor, lowerLoadFactor, loadFactor;
	@SuppressWarnings("rawtypes")
	private LinkedList[] buckets;

	/**
	 * A default constructor.
	 * Constructs a new, empty table with default initial capacity (16)
	 * and load factor (0.75) and lower load factor (0.25).
	 */
	public MyHashSet(){
		this.capacity = DEFAULT_CAPACITY;
		this.upperLoadFactor = DEFAULT_UPPER_LOAD_FACTOR;
		this.lowerLoadFactor = DEFAULT_LOWER_LOAD_FACTOR;
		buckets = new LinkedList[this.capacity];
	}

	/**
	 * Constructs a new, empty table with the specified initial 
	 * capacity and the specified load factors.
	 * @param initialCapacity - the initial capacity of the hash table.
	 * @param upperLoadFactor - the upper load factor of the hash table.
	 * @param lowerLoadFactor - the lower load factor of the hash table.
	 */
	public MyHashSet(int initialCapacity, double upperLoadFactor, 
			double lowerLoadFactor){
		this.capacity = initialCapacity;
		this.upperLoadFactor = upperLoadFactor;
		this.lowerLoadFactor = lowerLoadFactor;
		buckets = new LinkedList[this.capacity];
	}

	/**
	 * Data constructor - builds the hash set by adding the elements
	 * into the input array one-by-one.
	 * If the same value appears twice (or more) in the list, it is
	 * ignored.
	 * The new table has the default values of initial capacity 
	 * (16), upper load factor (0.75), and lower load factor (0.25). 
	 * @param data Values to add to the set.
	 */
	public MyHashSet(String[] data){
		this.capacity = DEFAULT_CAPACITY;
		this.upperLoadFactor = DEFAULT_UPPER_LOAD_FACTOR;;
		this.lowerLoadFactor = DEFAULT_LOWER_LOAD_FACTOR;
		buckets = new LinkedList[this.capacity];
		// adds to the set the values in the array
		for(int i=0; i<data.length; i++){
			add(data[i]);
		}
	}

	/* 
	 * maps a String element into a bucket in the hash 
	 * by using its hashCode
	 */ 
	private int hash(Object data) {
		return Math.abs(data.hashCode() % buckets.length);
	}

	/**
	 * @return the number of elements in the table.
	 */
	public int size(){
		return this.size;
	}

	/**
	 * @return the capacity of the table.
	 */
	public int capacity(){
		return this.capacity;
	}

	/**
	 * Add a new element with value newValue into the table.
	 * @param newValue new value to add to the table.
	 * @return false iff newValue already exists in the table.
	 */
	@SuppressWarnings("unchecked")
	public boolean add(String newValue){
		// uses the hushFunction in order to get the place in the array
		int pos = hash(newValue);
		if(buckets[pos]!=null){
			// check if newValue already exists in the table
			if(buckets[pos].contains(newValue)){
				return false;
			}
		}
		if(buckets[pos]==null){
			buckets[pos] = new LinkedList<>();
		}
		// enters newValue to the table
		buckets[pos].add(newValue);
		size++;
		// checks If load factor > upper load factor
		if(loadFactorMode()>0){
			resize();
		}
		return true;
	}

	/* 
	 * adds an element to the array without changing the array size.
	 */ 
	@SuppressWarnings("unchecked")
	private void addWithoutResize(String newValue){
		// uses the hushFunction in order to get the place in the array
		int pos = hash(newValue);
		if(buckets[pos]==null){
			buckets[pos] = new LinkedList<>();
		}
		// enters newValue to the table
		buckets[pos].add(newValue);
	}

	/*
	 *  checks the status of the current load factor
	 */
	private int loadFactorMode(){
		this.loadFactor = (double)size/(double)capacity;
		// returns 1 if the load factor is higher than the upper load factor
		if(loadFactor > upperLoadFactor){
			return 1;
		}
		// returns -1 if the load factor is lower than the lower load factor
		if(loadFactor < lowerLoadFactor){
			return -1;
		}
		// returns 0 if lowerLoadFactor< loadFactor < upperLoadFactor
		else 
			return 0;
	}

	/*
	 * change the size of the array according to the load factor status
	 * */
	@SuppressWarnings("rawtypes")
	private void resize(){
		// checks the loadFactor mode
		if(loadFactorMode()>0){
			// copy bucket's values to temp array
			LinkedList[] tmp = buckets;
			// expands bucket's length
			this.buckets = new LinkedList[this.capacity*2]; 
			this.capacity = buckets.length;
			for(int i=0; i<tmp.length; i++){
				if(tmp[i]!=null){
					// creates new array including every item in all the lists that were in the old buckets array
					Object[] tmpValues = tmp[i].toArray();
					// adds the values to buckets
					for(int j=0; j<tmpValues.length; j++){
						addWithoutResize((String)tmpValues[j]);
					}
				}
			}
			return;
		}
		// checks the loadFactor mode
		if(loadFactorMode()<0){
			// copy bucket's values to temp array
			LinkedList[] tmp = buckets;
			// reduces bucket's length
			this.buckets = new LinkedList[this.capacity/2]; 
			this.capacity = buckets.length;
			for(int i=0; i<tmp.length; i++){
				if(tmp[i]!=null){
					// creates new array including every item in all the lists that were in the old buckets array
					Object[] tmpValues = tmp[i].toArray();
					// adds the values to buckets
					for(int j=0; j<tmpValues.length; j++){
						addWithoutResize((String)tmpValues[j]);
					}
				}
			}
			return;
		}
	}

	/**
	 * Look for an input value in the table.
	 * @param searchVal value to search for.
	 * @return true iff searchVal is found in the table.
	 */
	public boolean contains(String searchVal){
		// finds the position in the array
		int pos = hash(searchVal);
		// checks if the list is null
		if(buckets[pos]==null){
			return false;
		}
		return buckets[pos].contains(searchVal) ? true : false;
	}

	/**
	 * Remove the input element form the table.
	 * @param toDelete value to delete.
	 * @return true iff toDelete is found and deleted.
	 */
	public boolean delete(String toDelete){
		// finds the position in the array
		int pos = hash(toDelete);
		if(buckets[pos]==null || !buckets[pos].contains(toDelete)){
			return false;
		}
		// remove the string from the linked list
		buckets[pos].remove(toDelete);
		this.size--;
		// checks If load factor < lower load factor
		if(loadFactorMode()<0){
			resize();
		}
		return true;
	}



}
